package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceReceiverClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.FamilyRelationship;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IContentProcessController;
import org.trinity.yqyl.process.controller.base.IServiceReceiverClientProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientHealthInformationRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientInterestRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientOtherRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceReceiverClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClient;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientHealthInformation;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientInterest;
import org.trinity.yqyl.repository.business.entity.ServiceReceiverClientOther;
import org.trinity.yqyl.repository.business.entity.User;

@Service
public class ServiceReceiverClientProcessController extends
        AbstractAutowiredCrudProcessController<ServiceReceiverClient, ServiceReceiverClientDto, ServiceReceiverClientSearchingDto, IServiceReceiverClientRepository>
        implements IServiceReceiverClientProcessController {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IContentProcessController contentProcessController;

    @Autowired
    private IServiceReceiverClientHealthInformationRepository serviceReceiverClientHealthInformationRepository;

    @Autowired
    private IServiceReceiverClientInterestRepository serviceReceiverClientInterestRepository;

    @Autowired
    private IServiceReceiverClientOtherRepository serviceReceiverClientOtherRepository;

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceReceiverClientDto> addAll(final List<ServiceReceiverClientDto> dtos) throws IException {
        final User user = userRepository.findOneByUsername(getCurrentUsername());

        final List<ServiceReceiverClient> entities = new ArrayList<>();

        for (final ServiceReceiverClientDto item : dtos) {
            final ServiceReceiverClient entity = new ServiceReceiverClient();

            entity.setUser(user);
            entity.setName(item.getName());
            entity.setNickname(item.getName());
            entity.setFamilyRelationship(LookupParser.parse(FamilyRelationship.class, item.getFamilyRelationship().getCode()));
            entity.setStatus(ServiceReceiverClientStatus.PROPOSAL);
            entity.setRegistryDate(new Date());

            entity.setIdentityCardCopy(contentProcessController.create());

            getDomainEntityRepository().save(entity);

            final ServiceReceiverClientHealthInformation healthInformation = new ServiceReceiverClientHealthInformation();
            healthInformation.setServiceReceiverClient(entity);
            healthInformation.setServiceReceiverClientId(entity.getId());
            healthInformation.setStatus(RecordStatus.ACTIVE);
            serviceReceiverClientHealthInformationRepository.save(healthInformation);

            final ServiceReceiverClientInterest interest = new ServiceReceiverClientInterest();
            interest.setServiceReceiverClient(entity);
            interest.setServiceReceiverClientId(entity.getId());
            interest.setStatus(RecordStatus.ACTIVE);
            serviceReceiverClientInterestRepository.save(interest);

            final ServiceReceiverClientOther other = new ServiceReceiverClientOther();
            other.setServiceReceiverClient(entity);
            other.setServiceReceiverClientId(entity.getId());
            other.setStatus(RecordStatus.ACTIVE);
            serviceReceiverClientOtherRepository.save(other);

            entities.add(entity);
        }

        return getDomainObjectConverter().convert(getDomainEntityRepository().save(entities));
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void disable(final Long entityId) throws IException {
        final ServiceReceiverClient entity = getDomainEntityRepository().findOne(entityId);
        if (!entity.getUser().getUsername().equals(getCurrentUsername())) {
            getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
        }
        if (entity.getStatus() == ServiceReceiverClientStatus.PROPOSAL) {
            getDomainEntityRepository().delete(entity);
        } else {
            entity.setStatus(ServiceReceiverClientStatus.DISABLED);
            getDomainEntityRepository().save(entity);
        }
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void realname(final List<ServiceReceiverClientDto> data) throws IException {
        final List<ServiceReceiverClient> entities = data.stream().map(item -> {
            final ServiceReceiverClient serviceReceiverClient = getDomainEntityRepository().findOne(item.getId());

            if (!serviceReceiverClient.getUser().getUsername().equals(getCurrentUsername())) {
                return serviceReceiverClient;
            }

            getDomainObjectConverter().convertBack(item, serviceReceiverClient, CopyPolicy.SOURCE_IS_NOT_NULL);
            serviceReceiverClient.setStatus(ServiceReceiverClientStatus.REALNAME);

            return serviceReceiverClient;
        }).collect(Collectors.toList());

        getDomainEntityRepository().save(entities);
    }

    @Override
    protected void addRelationshipFields(final ServiceReceiverClient entity, final ServiceReceiverClientDto dto) throws IException {
        final User user = userRepository.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername());
        entity.setUser(user);
    }

    @Override
    protected void updateRelationshipFields(final ServiceReceiverClient entity, final ServiceReceiverClientDto dto) throws IException {
        if (entity.getStatus() == ServiceReceiverClientStatus.REALNAME && !getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR)) {
            dto.setName(null);
        }

        if (!getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR)) {
            dto.setIdentityCard(null);
            dto.setIdentityCardCopy(null);
            dto.setRegistryDate(null);
            dto.setCredentialType(null);
        }
    }
}
