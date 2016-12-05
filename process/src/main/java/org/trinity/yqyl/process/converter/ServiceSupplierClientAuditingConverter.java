package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingDto;
import org.trinity.yqyl.common.message.lookup.AuditingType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAuditing;

@Component
public class ServiceSupplierClientAuditingConverter
        extends AbstractLookupSupportObjectConverter<ServiceSupplierClientAuditing, ServiceSupplierClientAuditingDto> {
    private static enum ServiceSupplierClientAuditingRelationship {
        NA
    }

    @Autowired
    public ServiceSupplierClientAuditingConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceSupplierClientAuditingDto source, final ServiceSupplierClientAuditing target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
        copyObject(source::getOperator, target::getOperator, target::setOperator, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
        copyLookup(source::getType, target::getType, target::setType, AuditingType.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceSupplierClientAuditing source, final ServiceSupplierClientAuditingDto target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getComment, target::getComment, target::setComment, copyPolicy);
        copyObject(source::getOperator, target::getOperator, target::setOperator, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyMessage(source::getType, target::getType, target::setType, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceSupplierClientAuditing source, final ServiceSupplierClientAuditingDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceSupplierClientAuditingRelationship.class)) {
        case NA:
        default:
            break;
        }
    }

    @Override
    protected ServiceSupplierClientAuditing createFromInstance() {
        return new ServiceSupplierClientAuditing();
    }

    @Override
    protected ServiceSupplierClientAuditingDto createToInstance() {
        return new ServiceSupplierClientAuditingDto();
    }
}
