package org.trinity.yqyl.process.controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementSearchingDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.common.utils.YqylUtil;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IServiceOrderRequirementProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderOperationRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRepository;
import org.trinity.yqyl.repository.business.dataaccess.IServiceOrderRequirementRepository;
import org.trinity.yqyl.repository.business.dataaccess.IUserRepository;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;
import org.trinity.yqyl.repository.business.entity.User;

@Service
public class ServiceOrderRequirementProcessController extends
        AbstractAutowiredCrudProcessController<ServiceOrderRequirement, ServiceOrderRequirementDto, ServiceOrderRequirementSearchingDto, IServiceOrderRequirementRepository>
        implements IServiceOrderRequirementProcessController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IServiceOrderRepository serviceOrderRepository;

    @Autowired
    private IServiceOrderOperationRepository serviceOrderOperationRepository;

    @Override
    @Transactional(rollbackOn = IException.class)
    public void cancelRequirement(final Long entityId) throws IException {
        final ServiceOrderRequirement entity = getDomainEntityRepository().findOne(entityId);

        if (!entity.getUser().getUsername().equals(getCurrentUsername())) {
            getSecurityUtil().checkAccessRight(AccessRight.ADMINISTRATOR);
        }

        if (entity.getStatus() != ServiceOrderRequirementStatus.ACTIVE) {
            throw getExceptionFactory().createException(ErrorMessage.INCORRECT_STATUS);
        }

        entity.setStatus(ServiceOrderRequirementStatus.CANCELED);

        getDomainEntityRepository().save(entity);
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void catchRequirement(final Long entityId) throws IException {
        final User user = userRepository.findOneByUsername(getCurrentUsername());
        final ServiceInfo serviceInfo = user.getServiceSupplierClient().getServiceInfos().get(0);

        final ServiceOrderRequirement requirement = getDomainEntityRepository().findOne(entityId);
        requirement.setStatus(ServiceOrderRequirementStatus.IN_PROGRESS);
        getDomainEntityRepository().save(requirement);

        final ServiceOrder order = new ServiceOrder();
        order.setAddress(requirement.getAddress());
        order.setExpectedPaymentAmount(requirement.getPrice());
        order.setPaymentMethod(PaymentMethod.POS);
        order.setPaymentType(PaymentType.ONE_TIME);
        order.setPhone(requirement.getPhone());
        order.setPrice(requirement.getPrice());
        order.setServiceOrderRequirement(requirement);
        order.setServiceTime(requirement.getServiceTime());
        order.setStatus(OrderStatus.REQUEST_GRABBED);
        order.setUid(YqylUtil.randomServiceOrderId());
        order.setUser(requirement.getUser());
        order.setComment(requirement.getComment());
        order.setServiceInfo(serviceInfo);

        serviceOrderRepository.save(order);

        final ServiceOrderOperation operation = new ServiceOrderOperation();
        operation.setOperation(OrderOperation.REQUIREMENT_CAUGHT);
        operation.setOperator(user.getUsername());
        operation.setOrderStatus(OrderStatus.REQUEST_GRABBED);
        operation.setServiceOrder(order);
        operation.setStatus(RecordStatus.ACTIVE);
        operation.setTimestamp(new Date());

        serviceOrderOperationRepository.save(operation);
    }

    @Override
    protected void addRelationshipFields(final ServiceOrderRequirement entity, final ServiceOrderRequirementDto dto) throws IException {
        entity.setUser(userRepository.findOneByUsername(getCurrentUsername()));
    }

    @Override
    protected boolean canAccessAllStatus() {
        return true;
    }

    @Override
    protected boolean canAccessScopeAll() {
        return getSecurityUtil().hasAccessRight(AccessRight.SERVICE_SUPPLIER)
                || getSecurityUtil().hasAccessRight(AccessRight.ADMINISTRATOR);
    }
}
