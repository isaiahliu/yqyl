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
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.AccountBalance;
import org.trinity.yqyl.repository.business.entity.Yiquan;

@Component
public class YiquanConverter extends AbstractLookupSupportObjectConverter<Yiquan, YiquanDto> {
    private static enum YiquanRelationship {
        BALANCE,
        NA
    }

    @Autowired
    private IObjectConverter<AccountBalance, AccountBalanceDto> accountBalanceConverter;

    @Autowired
    public YiquanConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final YiquanDto source, final Yiquan target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final Yiquan source, final YiquanDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final Yiquan source, final YiquanDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(YiquanRelationship.class)) {
        case BALANCE:
            copyRelationship(() -> {
                return source.getAccount().getBalances().stream().filter(item -> item.getCategory() == AccountCategory.YIQUAN).findFirst()
                        .get();
            }, target::setBalance, accountBalanceConverter, relationshipExpression);
            break;
        case NA:
        default:
            break;
        }
    }

    @Override
    protected Yiquan createFromInstance() {
        return new Yiquan();
    }

    @Override
    protected YiquanDto createToInstance() {
        return new YiquanDto();
    }
}
