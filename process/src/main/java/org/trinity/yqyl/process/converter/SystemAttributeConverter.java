package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeDto;
import org.trinity.yqyl.repository.business.entity.SystemAttribute;

@Component
public class SystemAttributeConverter extends AbstractLookupSupportObjectConverter<SystemAttribute, SystemAttributeDto> {
    private static enum SystemAttributeRelationship {
        NA
    }

    @Autowired
    public SystemAttributeConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final SystemAttributeDto source, final SystemAttribute target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertInternal(final SystemAttribute source, final SystemAttributeDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final SystemAttribute source, final SystemAttributeDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(SystemAttributeRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected SystemAttribute createFromInstance() {
        return new SystemAttribute();
    }

    @Override
    protected SystemAttributeDto createToInstance() {
        return new SystemAttributeDto();
    }
}
