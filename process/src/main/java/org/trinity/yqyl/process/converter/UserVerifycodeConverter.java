package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.VerifyCodeType;
import org.trinity.yqyl.repository.business.entity.UserVerifycode;

@Component
public class UserVerifycodeConverter extends AbstractLookupSupportObjectConverter<UserVerifycode, UserVerifycodeDto> {
    private static enum UserVerifycodeRelationship {
        NA
    }

    @Autowired
    public UserVerifycodeConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final UserVerifycodeDto source, final UserVerifycode target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
        copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
        copyLookup(source::getType, target::getType, target::setType, VerifyCodeType.class, copyPolicy);
    }

    @Override
    protected void convertInternal(final UserVerifycode source, final UserVerifycodeDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getId, target::getId, target::setId, copyPolicy);
        copyObject(source::getCode, target::getCode, target::setCode, copyPolicy);
        copyObject(source::getTimestamp, target::getTimestamp, target::setTimestamp, copyPolicy);
        copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
        copyMessage(source::getType, target::getType, target::setType, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final UserVerifycode source, final UserVerifycodeDto target,
            final RelationshipExpression relationshipExpression) {
        switch (relationshipExpression.getName(UserVerifycodeRelationship.class)) {
        case NA:
        default:
            break;
        }
    }

    @Override
    protected UserVerifycode createFromInstance() {
        return new UserVerifycode();
    }

    @Override
    protected UserVerifycodeDto createToInstance() {
        return new UserVerifycodeDto();
    }
}
