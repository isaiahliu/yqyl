package org.trinity.yqyl.process.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.UserDto;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeDto;
import org.trinity.yqyl.common.message.lookup.UserStatus;
import org.trinity.yqyl.repository.business.entity.User;
import org.trinity.yqyl.repository.business.entity.UserVerifycode;

@Component
public class UserConverter extends AbstractLookupSupportObjectConverter<User, UserDto> {
	private static enum UserRelationship {
		TOKEN, VERIFY_CODES, NA
	}

	@Autowired
	private IObjectConverter<UserVerifycode, UserVerifycodeDto> userVerifyCodeConverter;

	@Autowired
	public UserConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final UserDto source, final User target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getUsername, target::getUsername, target::setUsername, copyPolicy);
		copyObject(source::getPassword, target::getPassword, target::setPassword, copyPolicy);
		copyObject(source::getCellphone, target::getCellphone, target::setCellphone, copyPolicy);
		copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
		copyLookup(source::getStatus, target::getStatus, target::setStatus, UserStatus.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final User source, final UserDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getUsername, target::getUsername, target::setUsername, copyPolicy);
		copyObject(() -> "******", target::getPassword, target::setPassword, copyPolicy);
		copyObject(source::getCellphone, target::getCellphone, target::setCellphone, copyPolicy);
		copyObject(source::getEmail, target::getEmail, target::setEmail, copyPolicy);
		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
		copyMessageList(source::getAccessrights, target::getAccessrights, target::setAccessrights, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final User source, final UserDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(UserRelationship.class)) {
			case TOKEN:
				final Optional<Date> date = source.getTokens().stream().map(item -> item.getLastActiveTimestamp())
						.sorted((a, b) -> a.compareTo(b)).findFirst();
				if (date.isPresent()) {
					target.setLastAccessDate(date.get());
				}
				break;

			case VERIFY_CODES:
				copyRelationshipList(source::getUserVerifycodes, target::setVerifyCodes, userVerifyCodeConverter, relationshipExpression);
				break;
			case NA:
			default:
				break;
		}
	}

	@Override
	protected User createFromInstance() {
		return new User();
	}

	@Override
	protected UserDto createToInstance() {
		return new UserDto();
	}
}
