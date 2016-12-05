package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AllowanceSupplierClientDto;
import org.trinity.yqyl.repository.business.entity.AllowanceSupplierClient;

@Component
public class AllowanceSupplierClientConverter
        extends AbstractLookupSupportObjectConverter<AllowanceSupplierClient, AllowanceSupplierClientDto> {
    private static enum AllowanceSupplierClientRelationship {
        NA
    }

    @Autowired
    public AllowanceSupplierClientConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final AllowanceSupplierClientDto source, final AllowanceSupplierClient target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertInternal(final AllowanceSupplierClient source, final AllowanceSupplierClientDto target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final AllowanceSupplierClient source, final AllowanceSupplierClientDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(AllowanceSupplierClientRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected AllowanceSupplierClient createFromInstance() {
        return new AllowanceSupplierClient();
    }

    @Override
    protected AllowanceSupplierClientDto createToInstance() {
        return new AllowanceSupplierClientDto();
    }
}
