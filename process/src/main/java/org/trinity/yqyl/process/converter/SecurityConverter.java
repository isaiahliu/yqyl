package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.SecurityDto;
import org.trinity.yqyl.repository.business.entity.User;

@Component
public class SecurityConverter extends AbstractLookupSupportObjectConverter<User, SecurityDto> {
    @Autowired
    public SecurityConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
        super(lookupConverter);
    }

    @Override
    protected void convertBackInternal(final SecurityDto source, final User target, final CopyPolicy copyPolicy) {
        copyObject(source::getUsername, target::getUsername, target::setUsername, copyPolicy);
        copyObject(source::getPassword, target::getPassword, target::setPassword, copyPolicy);
    }

    @Override
    protected void convertInternal(final User source, final SecurityDto target, final CopyPolicy copyPolicy) {
        copyObject(source::getUsername, target::getUsername, target::setUsername, copyPolicy);
        copyObject(source::getPassword, target::getPassword, target::setPassword, copyPolicy);
    }

    @Override
    protected void convertRelationshipInternal(final User source, final SecurityDto target,
            final RelationshipExpression relationshipExpression) {
    }

    @Override
    protected User createFromInstance() {
        return new User();
    }

    @Override
    protected SecurityDto createToInstance() {
        return new SecurityDto();
    }

}
