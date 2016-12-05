package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AccountBalanceDto;
import org.trinity.yqyl.common.message.dto.domain.AccountDto;
import org.trinity.yqyl.common.message.lookup.AccountStatus;
import org.trinity.yqyl.repository.business.entity.Account;
import org.trinity.yqyl.repository.business.entity.AccountBalance;

@Component
public class AccountConverter extends AbstractLookupSupportObjectConverter<Account, AccountDto> {
    private static enum AccountRelationship {
        BALANCE,
        NA
    }

    @Autowired
    private IObjectConverter<AccountBalance, AccountBalanceDto> accountBalanceConverter;

    @Autowired
    public AccountConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final AccountDto source, final Account target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, AccountStatus.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final Account source, final AccountDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final Account source, final AccountDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(AccountRelationship.class)) {
        case BALANCE:
            copyRelationshipList(source::getBalances, target::setBalances, accountBalanceConverter, relationshipExpression);
            break;
        case NA:
        default:
            break;
        }
    }

    @Override
    protected Account createFromInstance() {
        return new Account();
    }

    @Override
    protected AccountDto createToInstance() {
        return new AccountDto();
    }
}
