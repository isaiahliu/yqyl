package org.trinity.yqyl.process.converter;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderOperationDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierStaffDto;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;
import org.trinity.yqyl.repository.business.entity.AccountTransaction;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderAppraise;
import org.trinity.yqyl.repository.business.entity.ServiceOrderOperation;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierStaff;

@Component
public class ServiceOrderConverter extends AbstractLookupSupportObjectConverter<ServiceOrder, ServiceOrderDto> {
    private static enum ServiceOrderRelationship {
        SERVICE_INFO,
        APPRAISE,
        STAFF,
        OPERATIONS,
        TRANSACTION,
        NA
    }

    @Autowired
    private IObjectConverter<ServiceInfo, ServiceInfoDto> serviceInfoConverter;

    @Autowired
    private IObjectConverter<ServiceSupplierStaff, ServiceSupplierStaffDto> serviceSupplierStaffConverter;

    @Autowired
    private IObjectConverter<ServiceOrderAppraise, ServiceOrderAppraiseDto> serviceOrderAppraiseConverter;

    @Autowired
    private IObjectConverter<ServiceOrderOperation, ServiceOrderOperationDto> serviceOrderOperationConverter;

    @Autowired
    private IObjectConverter<AccountTransaction, AccountTransactionDto> accountTransactionConverter;

    @Autowired
    public ServiceOrderConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceOrderDto source, final ServiceOrder target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getExpectedPaymentAmount, target::getExpectedPaymentAmount, target::setExpectedPaymentAmount, copyPolicy);
        copyObject(source::getActualPaymentAmount, target::getActualPaymentAmount, target::setActualPaymentAmount, copyPolicy);
        copyObject(source::getProposalTime, target::getProposalTime, target::setProposalTime, copyPolicy);
        copyObject(source::getApprovalTime, target::getApprovalTime, target::setApprovalTime, copyPolicy);
        copyObject(source::getSettledTime, target::getSettledTime, target::setSettledTime, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, OrderStatus.class, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getPhone, target::getPhone, target::setPhone, copyPolicy);
        copyObject(source::getReceipt, target::getReceipt, target::setReceipt, copyPolicy);
        copyLookup(source::getPaymentMethod, target::getPaymentMethod, target::setPaymentMethod, PaymentMethod.class, copyPolicy);
        copyLookup(source::getPaymentType, target::getPaymentType, target::setPaymentType, PaymentType.class, copyPolicy);
        copyObject(() -> {
            final Date serviceDate = source.getServiceDate();
            if (serviceDate == null) {
                return null;
            }

            if (source.getServiceHour() != null) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(serviceDate);
                calendar.set(Calendar.HOUR_OF_DAY, source.getServiceHour());

                return calendar.getTime();
            }

            return serviceDate;
        }, target::getServiceTime, target::setServiceTime, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceOrder source, final ServiceOrderDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getExpectedPaymentAmount, target::getExpectedPaymentAmount, target::setExpectedPaymentAmount, copyPolicy);
        copyObject(source::getActualPaymentAmount, target::getActualPaymentAmount, target::setActualPaymentAmount, copyPolicy);
        copyObject(source::getProposalTime, target::getProposalTime, target::setProposalTime, copyPolicy);
        copyObject(source::getApprovalTime, target::getApprovalTime, target::setApprovalTime, copyPolicy);
        copyObject(source::getSettledTime, target::getSettledTime, target::setSettledTime, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getPhone, target::getPhone, target::setPhone, copyPolicy);
        copyObject(source::getReceipt, target::getReceipt, target::setReceipt, copyPolicy);
        copyObject(source::getServiceTime, target::getServiceDate, target::setServiceDate, copyPolicy);
        copyMessage(source::getPaymentMethod, target::getPaymentMethod, target::setPaymentMethod, copyPolicy);
        copyMessage(source::getPaymentType, target::getPaymentType, target::setPaymentType, copyPolicy);
        copyObject(() -> {
            final Date serviceTime = source.getServiceTime();
            if (serviceTime == null) {
                return null;
            }

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(serviceTime);
            return calendar.get(Calendar.HOUR_OF_DAY);
        }, target::getServiceHour, target::setServiceHour, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceOrder source, final ServiceOrderDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceOrderRelationship.class)) {
        case APPRAISE:
            copyRelationship(source::getAppraise, target::setAppraise, serviceOrderAppraiseConverter, relationshipExpression);
            break;
        case SERVICE_INFO:
            copyRelationship(source::getServiceInfo, target::setServiceInfo, serviceInfoConverter, relationshipExpression);
            break;
        case STAFF:
            copyRelationship(source::getServiceSupplierStaff, target::setStaff, serviceSupplierStaffConverter, relationshipExpression);
            break;
        case OPERATIONS:
            copyRelationshipList(source::getOperations, target::setOperations, serviceOrderOperationConverter, relationshipExpression);
            break;
        case TRANSACTION:
            copyRelationship(source::getAccountTransaction, target::setTransaction, accountTransactionConverter, relationshipExpression);
            break;
        default:
            break;
        }

    }

    @Override
    protected ServiceOrder createFromInstance() {
        return new ServiceOrder();
    }

    @Override
    protected ServiceOrderDto createToInstance() {
        return new ServiceOrderDto();
    }
}
