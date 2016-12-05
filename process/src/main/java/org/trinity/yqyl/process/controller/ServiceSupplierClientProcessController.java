package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.trinity.common.dto.object.ISearchingDto;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.AuditingType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAccountProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientAuditingProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientMaterialProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierClientProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IContentRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceCategoryRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientAccountRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientAuditingRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientMaterialRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.Content;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAccount;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAuditing;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientMaterial;
import org.trinity.yqyl.repository.business.entity.User;

@Service
public class ServiceSupplierClientProcessController extends
        AbstractAutowiredCrudProcessController<ServiceSupplierClient, ServiceSupplierClientDto, ServiceSupplierClientSearchingDto, IServiceSupplierClientRepository>
        implements IServiceSupplierClientProcessController {
    @Autowired
    private IServiceCategoryRepository serviceCategoryRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IServiceSupplierClientAccountRepository serviceSupplierClientAccountRepository;

    @Autowired
    private IServiceSupplierClientMaterialRepository serviceSupplierClientMaterialRepository;

    @Autowired
    private IServiceSupplierClientAuditingRepository serviceSupplierClientAuditingRepository;

    @Autowired
    private IContentRepository contentRepository;

    @Autowired
    private IServiceSupplierClientAccountProcessController supplierClientAccountProcessController;

    @Autowired
    private IServiceSupplierClientMaterialProcessController supplierClientMaterialProcessController;

    @Autowired
    private IServiceSupplierClientAuditingProcessController serviceSupplierClientAuditingProcessController;

    @Autowired
    private IAccountProcessController accountProcessController;

    @Override
    @Transactional(rollbackOn = IException.class)
    public void audit(final List<ServiceSupplierClientDto> serviceSupplierClientDtos) throws IException {
        final Iterable<ServiceSupplierClient> entities = getDomainEntityRepository()
                .findAll(serviceSupplierClientDtos.stream().map(item -> item.getId()).collect(Collectors.toList()));
        for (final ServiceSupplierClient item : entities) {
            item.getUser().getAccessrights().add(AccessRight.SERVICE_SUPPLIER);

            userRepository.save(item.getUser());

            item.setStatus(ServiceSupplierClientStatus.ACTIVE);

            if (item.getAccount() == null) {
                item.setAccount(accountProcessController.createAccount());
            }

            final ServiceSupplierClientAuditing auditing = new ServiceSupplierClientAuditing();

            auditing.setComment("");
            try {
                auditing.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e) {
            }
            auditing.setStatus(RecordStatus.ACTIVE);
            auditing.setType(AuditingType.APPLY);
            auditing.setTimestamp(new Date());
            auditing.setServiceSupplierClient(item);

            serviceSupplierClientAuditingRepository.save(auditing);
        }

        getDomainEntityRepository().save(entities);
    }

    @Override
    public Page<ServiceSupplierClientDto> getAll(final ServiceSupplierClientSearchingDto searchingData) throws IException {
        if (searchingData.getCategoryChildren().isEmpty()) {
            if (searchingData.getCategoryParent() != null) {
                final ServiceCategory category = serviceCategoryRepository.findOne(searchingData.getCategoryParent());
                if (category != null) {
                    searchingData.getCategoryChildren().addAll(serviceCategoryRepository.findAllByParent(category).stream()
                            .map(item -> item.getId()).collect(Collectors.toList()));
                }
            }
        }

        final Page<ServiceSupplierClientDto> dtos = super.getAll(searchingData);
        dtos.forEach(item -> {
            if (item.getServiceInfos() != null && !item.getServiceInfos().isEmpty()) {
                if (!searchingData.getCategoryChildren().isEmpty()) {
                    item.setExpectedPrice(item.getServiceInfos().stream()
                            .filter(info -> searchingData.getCategoryChildren().contains(info.getServiceCategory().getId()))
                            .collect(Collectors.summarizingDouble(info -> info.getPrice())).getSum());
                } else {
                    item.setExpectedPrice(
                            item.getServiceInfos().stream().collect(Collectors.summarizingDouble(info -> info.getPrice())).getSum());
                }
            }
        });

        return dtos;
    }

    @Override
    public Page<ServiceSupplierClientDto> listPublicInfo(final ServiceSupplierClientSearchingDto request) throws IException {
        final ServiceSupplierClientSearchingDto filter = new ServiceSupplierClientSearchingDto();

        filter.setName(request.getName());
        filter.setAddress(request.getAddress());
        filter.getStatus().add(ServiceSupplierClientStatus.ACTIVE.getMessageCode());
        filter.setSearchScope(ISearchingDto.SEARCH_ALL);

        return getDomainEntityRepository().query(filter, getPagingConverter().convert(request)).map(item -> {
            final ServiceSupplierClientDto dto = new ServiceSupplierClientDto();
            dto.setName(item.getName());
            dto.setAddress(item.getAddress());
            dto.setContactPhone(item.getContactPhone());

            return dto;
        });
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void propose(final ServiceSupplierClientDto dto) throws IException {
        User user = null;

        if (dto.getId() > 0 && getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR)) {
            user = userRepository.findOne(dto.getId());
        } else {
            user = userRepository.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername());
        }

        final ServiceSupplierClient entity = user.getServiceSupplierClient();

        switch (entity.getStatus()) {
        case INACTIVE:
        case REJECTED:
            if (ServiceSupplierClientStatus.PROPOSAL.getMessageCode().equals(dto.getStatus().getCode())) {
                final ServiceSupplierClientAuditingDto auditingDto = new ServiceSupplierClientAuditingDto();
                auditingDto.setId(entity.getUserId());
                auditingDto.setType(new LookupDto(AuditingType.PROPOSAL));

                dto.getAuditings().add(auditingDto);
            }
            break;
        case PROPOSAL: {
            final ServiceSupplierClientAuditingDto auditingDto = new ServiceSupplierClientAuditingDto();
            auditingDto.setId(entity.getUserId());
            auditingDto.setType(new LookupDto(AuditingType.PROPOSAL_UPDATE));

            dto.getAuditings().add(auditingDto);
        }
            break;
        case ACTIVE: {
            final ServiceSupplierClientAuditingDto auditingDto = new ServiceSupplierClientAuditingDto();
            auditingDto.setId(entity.getUserId());
            auditingDto.setType(new LookupDto(AuditingType.UPDATE_REGULAR_INFO));

            dto.getAuditings().add(auditingDto);
            dto.setBankAccount(null);
            dto.setMaterial(null);
            dto.setName(null);
            dto.setCategories(null);
            dto.setLogo(null);
            dto.setRegion(null);
            dto.setType(null);
        }
            break;
        case DISABLED:
        case AWAITING_PAYMENT:
        default:
            return;
        }

        updateRelationshipFields(entity, dto);
        updateRelatedTables(entity, dto);

        getDomainObjectConverter().convertBack(dto, entity, CopyPolicy.SOURCE_IS_NOT_NULL);

        getDomainEntityRepository().save(entity);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public ServiceSupplierClientDto register() throws IException {
        final ServiceSupplierClientSearchingDto searchingDto = new ServiceSupplierClientSearchingDto();
        searchingDto.setRsexp("account");

        final User user = userRepository.findOneByUsername(getSecurityUtil().getCurrentToken().getUsername());
        if (user.getServiceSupplierClient() != null) {
            return getDomainObjectConverter().convert(user.getServiceSupplierClient(), searchingDto.generateRelationship());
        }

        Content content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);

        contentRepository.save(content);

        final ServiceSupplierClient serviceSupplierClient = new ServiceSupplierClient();
        serviceSupplierClient.setUserId(user.getId());
        serviceSupplierClient.setUser(user);
        serviceSupplierClient.setStatus(ServiceSupplierClientStatus.INACTIVE);
        serviceSupplierClient.setLogo(content.getUuid());

        getDomainEntityRepository().save(serviceSupplierClient);

        final ServiceSupplierClientAccount serviceSupplierClientAccount = new ServiceSupplierClientAccount();
        serviceSupplierClientAccount.setServiceSupplierClient(serviceSupplierClient);
        serviceSupplierClientAccount.setServiceSupplierClientId(serviceSupplierClient.getUserId());
        serviceSupplierClientAccount.setStatus(RecordStatus.ACTIVE);
        serviceSupplierClientAccountRepository.save(serviceSupplierClientAccount);

        final ServiceSupplierClientMaterial serviceSupplierClientMaterial = new ServiceSupplierClientMaterial();
        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setApplicationCopy(content.getUuid());

        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setBusinessLicenseCopy(content.getUuid());

        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setContractCopy(content.getUuid());

        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setCorporateCheckingCopy(content.getUuid());

        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setBusinessCertificateCopy(content.getUuid());

        content = new Content();
        content.setStatus(RecordStatus.ACTIVE);
        content.setUuid(UUID.randomUUID().toString());
        content.setContent(new byte[0]);
        contentRepository.save(content);

        serviceSupplierClientMaterial.setLicenseCopy(content.getUuid());

        serviceSupplierClientMaterial.setServiceSupplierClient(serviceSupplierClient);
        serviceSupplierClientMaterial.setServiceSupplierClientId(serviceSupplierClient.getUserId());
        serviceSupplierClientMaterial.setStatus(RecordStatus.ACTIVE);

        serviceSupplierClientMaterialRepository.save(serviceSupplierClientMaterial);

        serviceSupplierClient.setBankAccount(serviceSupplierClientAccount);
        serviceSupplierClient.setMaterial(serviceSupplierClientMaterial);

        return getDomainObjectConverter().convert(serviceSupplierClient, searchingDto.generateRelationship());
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void reject(final List<ServiceSupplierClientDto> serviceSupplierClientDtos) throws IException {
        final Iterable<ServiceSupplierClient> entities = getDomainEntityRepository()
                .findAll(serviceSupplierClientDtos.stream().map(item -> item.getId()).collect(Collectors.toList()));

        entities.forEach(item -> {
            item.setStatus(ServiceSupplierClientStatus.REJECTED);

            final ServiceSupplierClientAuditing auditing = new ServiceSupplierClientAuditing();

            auditing.setComment(item.getAuditings().get(0).getComment());
            try {
                auditing.setOperator(getSecurityUtil().getCurrentToken().getUsername());
            } catch (final IException e) {
            }
            auditing.setStatus(RecordStatus.ACTIVE);
            auditing.setType(AuditingType.REJECT);
            auditing.setTimestamp(new Date());

            item.addAuditing(auditing);
        });

        getDomainEntityRepository().save(entities);
    }

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }

    @Override
    protected boolean canAccessScopeAll() {
        return true;
    }

    @Override
    protected void updateRelationshipFields(final ServiceSupplierClient entity, final ServiceSupplierClientDto dto) throws IException {
        if (dto.getBankAccount() != null) {
            final List<ServiceSupplierClientAccountDto> supplierClientAccountDtos = new ArrayList<>();

            dto.getBankAccount().setId(dto.getId());

            supplierClientAccountDtos.add(dto.getBankAccount());
            supplierClientAccountProcessController.updateAll(supplierClientAccountDtos);
        }

        if (dto.getMaterial() != null) {
            final List<ServiceSupplierClientMaterialDto> supplierClientMaterialDtos = new ArrayList<>();

            dto.getMaterial().setId(dto.getId());

            supplierClientMaterialDtos.add(dto.getMaterial());
            supplierClientMaterialProcessController.updateAll(supplierClientMaterialDtos);
        }

        if (!dto.getAuditings().isEmpty()) {
            final ServiceSupplierClientDto serviceSupplierClient = new ServiceSupplierClientDto();
            serviceSupplierClient.setId(entity.getUserId());

            dto.getAuditings().forEach(item -> {
                try {
                    item.setOperator(getSecurityUtil().getCurrentToken().getUsername());
                } catch (final IException e) {
                }
                item.setStatus(new LookupDto(RecordStatus.ACTIVE));
                item.setTimestamp(new Date());
                item.setServiceSupplierClient(serviceSupplierClient);
            });

            serviceSupplierClientAuditingProcessController.addAll(dto.getAuditings());
        }

        super.updateRelationshipFields(entity, dto);
    }
}
