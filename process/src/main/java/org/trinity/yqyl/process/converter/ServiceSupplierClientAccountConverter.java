package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAccountDto;
import org.trinity.yqyl.common.message.lookup.AccountType;
import org.trinity.yqyl.common.message.lookup.Bank;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.ServiceSupplierClientAccount;

@Component
public class ServiceSupplierClientAccountConverter
        extends AbstractLookupSupportObjectConverter<ServiceSupplierClientAccount, ServiceSupplierClientAccountDto> {
    private static enum ServiceSupplierClientAccountRelationship {
        NA
    }

    @Autowired
    public ServiceSupplierClientAccountConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final ServiceSupplierClientAccountDto source, final ServiceSupplierClientAccount target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getServiceSupplierClientId, target::setServiceSupplierClientId, copyPolicy);
        copyLookup(source::getType, target::getType, target::setType, AccountType.class, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
        copyLookup(source::getBank, target::getBank, target::setBank, Bank.class, copyPolicy);
        copyObject(source::getAccount, target::getAccount, target::setAccount, copyPolicy);
        copyObject(source::getClientName, target::getClientName, target::setClientName, copyPolicy);
    }

    @Override
    protected void convertInternal(final ServiceSupplierClientAccount source, final ServiceSupplierClientAccountDto target,
            final CopyPolicy copyPolicy) {
        copyObject(source::getServiceSupplierClientId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getType, target::getType, target::setType, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyMessage(source::getBank, target::getBank, target::setBank, copyPolicy);
        copyObject(source::getAccount, target::getAccount, target::setAccount, copyPolicy);
        copyObject(source::getClientName, target::getClientName, target::setClientName, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final ServiceSupplierClientAccount source, final ServiceSupplierClientAccountDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(ServiceSupplierClientAccountRelationship.class)) {
        default:
            break;
        }
    }

    @Override
    protected ServiceSupplierClientAccount createFromInstance() {
        return new ServiceSupplierClientAccount();
    }

    @Override
    protected ServiceSupplierClientAccountDto createToInstance() {
        return new ServiceSupplierClientAccountDto();
    }
}
