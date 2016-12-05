package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.process.converter.IObjectConverter.CopyPolicy;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffSearchingDto;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.StaffStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceSupplierStaffProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IContentRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceCategoryRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierClientRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceSupplierStaffRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.Content;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceCategory_;
import org.trinity.yqyl.repository.business.entity.ServiceOrder_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient_;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff_;
import org.trinity.yqyl.repository.business.entity.User_;

@Service
public class ServiceSupplierStaffProcessController extends
        AbstractAutowiredCrudProcessController<ServiceSupplierStaff, ServiceSupplierStaffDto, ServiceSupplierStaffSearchingDto, IServiceSupplierStaffRepository>
        implements IServiceSupplierStaffProcessController {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IServiceSupplierClientRepository serviceSupplierClientRepository;

    @Autowired
    private IContentRepository contentRepository;

    @Autowired
    private IServiceCategoryRepository serviceCategoryRepository;

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceSupplierStaffDto> addAll(final List<ServiceSupplierStaffDto> data) throws IException {
        for (final ServiceSupplierStaffDto dto : data) {
            final ServiceSupplierStaff entity = getDomainObjectConverter().convertBack(dto);
            final ServiceSupplierClient serviceSupplierClient = userRepository
                    .findOneByUsername(getSecurityUtil().getCurrentToken().getUsername()).getServiceSupplierClient();

            if (dto.getServiceSupplierClient() != null && dto.getServiceSupplierClient().getId() != null
                    && dto.getServiceSupplierClient().getId() > 0
                    && dto.getServiceSupplierClient().getId() != serviceSupplierClient.getUserId()) {
                getSecurityUtil().checkAccessRight(AccessRight.OPERATOR);

                entity.setServiceSupplierClient(serviceSupplierClientRepository.findOne(dto.getServiceSupplierClient().getId()));
            } else {
                entity.setServiceSupplierClient(serviceSupplierClient);
            }

            final Content content = new Content();
            content.setContent(new byte[0]);
            content.setStatus(RecordStatus.ACTIVE);
            content.setUuid(UUID.randomUUID().toString());
            contentRepository.save(content);

            entity.setPhoto(content.getUuid());

            if (!dto.getServiceCategories().isEmpty()) {
                final List<ServiceCategory> serviceCategories = new ArrayList<>();
                serviceCategoryRepository
                        .findAll(dto.getServiceCategories().stream().map(item -> item.getId()).collect(Collectors.toList()))
                        .forEach(item -> serviceCategories.add(item));

                entity.setServiceCategories(serviceCategories);
            }

            getDomainEntityRepository().save(entity);

            getDomainObjectConverter().convert(entity, dto, CopyPolicy.TARGET_IS_NULL);
        }

        return data;
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public List<ServiceSupplierStaffDto> getAvailableStaffs(final ServiceSupplierStaffSearchingDto searchingData) throws IException {
        final String username = getSecurityUtil().getCurrentToken().getUsername();
        final Specification<ServiceSupplierStaff> unAvailableSpecification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.equal(root.join(ServiceSupplierStaff_.serviceSupplierClient).join(ServiceSupplierClient_.user).get(User_.username),
                            username));

            predicates.add(cb.equal(root.get(ServiceSupplierStaff_.status), StaffStatus.ACTIVE));

            predicates.add(cb.in(root.join(ServiceSupplierStaff_.serviceOrders).get(ServiceOrder_.status)).value(OrderStatus.IN_PROGRESS)
                    .value(OrderStatus.AWAITING_PAYMENT));

            query.distinct(true);

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        final List<Long> unavailableStaffIds = getDomainEntityRepository().findAll(unAvailableSpecification).stream()
                .map(item -> item.getId()).collect(Collectors.toList());

        final Specification<ServiceSupplierStaff> specification = (root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();

            predicates.add(
                    cb.equal(root.join(ServiceSupplierStaff_.serviceSupplierClient).join(ServiceSupplierClient_.user).get(User_.username),
                            username));

            predicates.add(cb.equal(root.join(ServiceSupplierStaff_.serviceCategories).get(ServiceCategory_.id),
                    searchingData.getServiceCategoryId()));

            predicates.add(cb.equal(root.get(ServiceSupplierStaff_.status), StaffStatus.ACTIVE));

            if (!unavailableStaffIds.isEmpty()) {
                final In<Long> in = cb.in(root.get(ServiceSupplierStaff_.id));
                unavailableStaffIds.forEach(item -> in.value(item));
                predicates.add(cb.not(in));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return getDomainObjectConverter().convert(getDomainEntityRepository().findAll(specification), searchingData.generateRelationship());
    }

    @Override
    protected boolean canAccessAllStatus() {
        return getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR)
                || getSecurityUtil().hasAccessRight(AccessRight.SERVICE_SUPPLIER);
    }

    @Override
    protected void updateRelationshipFields(final ServiceSupplierStaff entity, final ServiceSupplierStaffDto dto) {
        final List<ServiceCategoryDto> serviceCategories = dto.getServiceCategories();
        if (serviceCategories != null) {
            entity.getServiceCategories().clear();
            serviceCategoryRepository.findAll(serviceCategories.stream().map(item -> item.getId()).collect(Collectors.toList()))
                    .forEach(item -> entity.getServiceCategories().add(item));
        }
    }

    @Override
    protected void validateDataPermission(final ServiceSupplierStaffDto dto) throws IException {
        final String username = getDomainEntityRepository().findOne(dto.getId()).getServiceSupplierClient().getUser().getUsername();
        if (!getSecurityUtil().getCurrentToken().getUsername().equals(username)) {
            getSecurityUtil().checkAccessRight(AccessRight.SUPER_USER);
        }
        super.validateDataPermission(dto);
    }
}
