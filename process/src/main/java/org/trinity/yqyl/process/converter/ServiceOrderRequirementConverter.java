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
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderRequirementDto;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceOrderRequirement;

@Component
public class ServiceOrderRequirementConverter
        extends AbstractLookupSupportObjectConverter<ServiceOrderRequirement, ServiceOrderRequirementDto> {
    private static enum ServiceOrderRequirementRelationship {
        SERVICE_ORDERS,
        NA
    }

    @Autowired
    private IObjectConverter<ServiceOrder, ServiceOrderDto> serviceOrderConverter;

    @Autowired
    public ServiceOrderRequirementConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceOrderRequirementDto source, final ServiceOrderRequirement target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getPhone, target::getPhone, target::setPhone, copyPolicy);
        copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
        copyObject(source::getAnnounceTime, target::getAnnounceTime, target::setAnnounceTime, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, ServiceOrderRequirementStatus.class, copyPolicy);
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
    protected void convertInternal(final ServiceOrderRequirement source, final ServiceOrderRequirementDto target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getAddress, target::getAddress, target::setAddress, copyPolicy);
        copyObject(source::getPhone, target::getPhone, target::setPhone, copyPolicy);
        copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyObject(source::getAnnounceTime, target::getAnnounceTime, target::setAnnounceTime, copyPolicy);

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
    protected void convertRelationshipInternal(final ServiceOrderRequirement source, final ServiceOrderRequirementDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceOrderRequirementRelationship.class)) {
        case SERVICE_ORDERS:
            copyRelationshipList(source::getServiceOrders, target::setServiceOrders, serviceOrderConverter, relationshipExpression);
            break;
        default:
            break;
        }
    }

    @Override
    protected ServiceOrderRequirement createFromInstance() {
        return new ServiceOrderRequirement();
    }

    @Override
    protected ServiceOrderRequirementDto createToInstance() {
        return new ServiceOrderRequirementDto();
    }
}
