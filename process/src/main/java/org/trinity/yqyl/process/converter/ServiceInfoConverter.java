package org.trinity.yqyl.process.converter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoStasticDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.repository.business.entity.ServiceCategory;
import org.trinity.yqyl.repository.business.entity.ServiceInfo;
import org.trinity.yqyl.repository.business.entity.ServiceInfoStastic;
import org.trinity.yqyl.repository.business.entity.ServiceOrder;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClient;

@Component
public class ServiceInfoConverter extends AbstractLookupSupportObjectConverter<ServiceInfo, ServiceInfoDto> {
    private static enum ServiceInfoRelationship {
        SERVICE_SUPPLIER_CLIENT,
        SERVICE_CATEGORY,
        MONTHLY_INFO,
        STASTIC,
        IMAGES,
        NA;
    }

    @Autowired
    private IObjectConverter<ServiceCategory, ServiceCategoryDto> serviceCategoryConverter;

    @Autowired
    private IObjectConverter<ServiceSupplierClient, ServiceSupplierClientDto> serviceSupplierClientConverter;

    @Autowired
    private IObjectConverter<ServiceInfoStastic, ServiceInfoStasticDto> serviceInfoStasticConverter;

    @Autowired
    public ServiceInfoConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceInfoDto source, final ServiceInfo target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getImage, target::getImage, target::setImage, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, ServiceStatus.class, copyPolicy);
        copyLookup(source::getPaymentMethod, target::getPaymentMethod, target::setPaymentMethod, PaymentMethod.class, copyPolicy);
        copyLookup(source::getPaymentType, target::getPaymentType, target::setPaymentType, PaymentType.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceInfo source, final ServiceInfoDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getName, target::getName, target::setName, copyPolicy);
        copyObject(source::getDescription, target::getDescription, target::setDescription, copyPolicy);
        copyObject(source::getPrice, target::getPrice, target::setPrice, copyPolicy);
        copyObject(source::getImage, target::getImage, target::setImage, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyMessage(source::getPaymentMethod, target::getPaymentMethod, target::setPaymentMethod, copyPolicy);
        copyMessage(source::getPaymentType, target::getPaymentType, target::setPaymentType, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceInfo source, final ServiceInfoDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceInfoRelationship.class)) {
        case SERVICE_CATEGORY:
            copyRelationship(source::getServiceCategory, target::setServiceCategory, serviceCategoryConverter, relationshipExpression);
            break;
        case SERVICE_SUPPLIER_CLIENT:
            copyRelationship(source::getServiceSupplierClient, target::setServiceSupplierClient, serviceSupplierClientConverter,
                    relationshipExpression);
            break;
        case MONTHLY_INFO:
            final Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -1);
            final Date aMonthAgo = now.getTime();

            final List<ServiceOrder> orders = source.getServiceOrders().stream()
                    .filter(item -> item.getProposalTime() != null && item.getProposalTime().after(aMonthAgo)).collect(Collectors.toList());
            target.setMonthlyProposalOrderCount(orders.size());

            final Double averageRate = orders.stream().filter(item -> item.getAppraise() != null)
                    .map(item -> (item.getAppraise().getStaffRate() + item.getAppraise().getAttitudeRate()
                            + item.getAppraise().getOnTimeRate() + item.getAppraise().getQualityRate()) / 4)
                    .collect(Collectors.averagingDouble(item -> item));
            target.setMonthlyRate(averageRate);
            break;
        case STASTIC:
            copyRelationship(source::getServiceInfoStastic, target::setStastic, serviceInfoStasticConverter, relationshipExpression);
            break;
        case IMAGES:
            copyObject(source::getImages, target::getImages, target::setImages, CopyPolicy.SOURCE_IS_NOT_NULL);
        default:
            break;
        }
    }

    @Override
    protected ServiceInfo createFromInstance() {
        return new ServiceInfo();
    }

    @Override
    protected ServiceInfoDto createToInstance() {
        return new ServiceInfoDto();
    }
}
