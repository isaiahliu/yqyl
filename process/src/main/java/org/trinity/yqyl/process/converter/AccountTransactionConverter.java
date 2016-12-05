package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountTransactionDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.TransactionType;
import org.trinity.yqyl.repository.business.entity.AccountPosting;
import org.trinity.yqyl.repository.business.entity.AccountTransaction;

@Component
public class AccountTransactionConverter extends AbstractLookupSupportObjectConverter<AccountTransaction, AccountTransactionDto> {
    private static enum AccountTransactionRelationship {
        ACCOUNT_POSTINGS,
        NA
    }

    @Autowired
    private IObjectConverter<AccountPosting, AccountPostingDto> accountPostingConverter;

    @Autowired
    public AccountTransactionConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final AccountTransactionDto source, final AccountTransaction target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyLookup(source::getType, target::getType, target::setType, TransactionType.class, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
    }

    @Override
    protected void convertInternal(final AccountTransaction source, final AccountTransactionDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyMessage(source::getType, target::getType, target::setType, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final AccountTransaction source, final AccountTransactionDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(AccountTransactionRelationship.class)) {
        case ACCOUNT_POSTINGS:
            copyRelationshipList(source::getAccountPostings, target::setAccountPostings, accountPostingConverter, relationshipExpression);
            break;
        case NA:
        default:
            break;
        }
    }

    @Override
    protected AccountTransaction createFromInstance() {
        return new AccountTransaction();
    }

    @Override
    protected AccountTransactionDto createToInstance() {
        return new AccountTransactionDto();
    }
}
