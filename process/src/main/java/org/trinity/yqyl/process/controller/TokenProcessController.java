package org.trinity.yqyl.process.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trinity.common.accessright.AuthToken;
import org.trinity.common.accessright.TokenAuthenticationStatus;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.TokenDto;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.common.message.lookup.TokenStatus;
import org.trinity.yqyl.process.controller.base.ITokenProcessController;
import org.trinity.yqyl.repository.business.dataaccess.ISystemAttributeRepository;
import org.trinity.yqyl.repository.business.dataaccess.ITokenRepository;
import org.trinity.yqyl.repository.business.entity.SystemAttribute;
import org.trinity.yqyl.repository.business.entity.Token;
import org.trinity.yqyl.repository.business.entity.User;

@Service
public class TokenProcessController implements ITokenProcessController {
	@Autowired
	private ITokenRepository tokenRepository;

	@Autowired
	private IExceptionFactory exceptionFactory;

	@Autowired
	private IObjectConverter<Token, TokenDto> tokenConverter;

	@Autowired
	private ISystemAttributeRepository systemAttributeRepository;

	@Override
	public TokenDto getToken(final String tokenName) throws IException {
		final Token token = tokenRepository.findOneByToken(tokenName);

		if (token == null) {
			throw exceptionFactory.createException(GeneralErrorMessage.TOKEN_IS_MISSING);
		} else if (token.getStatus() != TokenStatus.AUTHENTICATED) {
			throw exceptionFactory.createException(ErrorMessage.TOKEN_IS_NOT_AUTHENTICATED);
		}

		return tokenConverter.convert(token);
	}

	@Override
	public AuthToken preAuth(final String token) {
		if (StringUtils.isEmpty(token)) {
			return null;
		}

		final Token tokenEntity = tokenRepository.findOneByToken(token);

		final AuthToken result = new AuthToken(token);

		try {
			if (tokenEntity == null) {
				result.setStatus(TokenAuthenticationStatus.NOT_EXISTS);
			}

			result.setFirstActiveTimestamp(tokenEntity.getActiveTimestamp());
			result.setLastActiveTimestamp(tokenEntity.getLastActiveTimestamp());

			final User user = tokenEntity.getUser();
			if (user != null) {
				result.setUsername(user.getUsername());
				result.setUserDetailKey(user.getUsername());
			}

			switch (tokenEntity.getStatus()) {
				case AUTHENTICATED:
					result.setStatus(TokenAuthenticationStatus.AUTHENTICATED);
					final SystemAttribute attribute = systemAttributeRepository.findOneByKey(SystemAttributeKey.TOKEN_EXPIRE_DAYS);

					if (attribute != null) {
						final int expireDays = Integer.parseInt(attribute.getValue());

						final Calendar calendar = Calendar.getInstance();
						calendar.setTime(tokenEntity.getLastActiveTimestamp());

						calendar.add(Calendar.DATE, expireDays);

						final Date expireDate = calendar.getTime();

						if (new Date().after(expireDate)) {
							result.setStatus(TokenAuthenticationStatus.TOKEN_IS_EXPIRED);
						}
					}

					break;
				case EXPIRED:
					result.setStatus(TokenAuthenticationStatus.TOKEN_IS_EXPIRED);
					break;
				case LOGGED_BY_OTHERS:
					result.setStatus(TokenAuthenticationStatus.LOGGED_BY_OTHERS);
					break;
				case PASSWORD_CHANGED:
					result.setStatus(TokenAuthenticationStatus.PASSWORD_CHANGED);
					break;
				case UNAUTHENTICATED:
					result.setStatus(TokenAuthenticationStatus.UNAUTHENTICATED);
					break;
				case LOGGED_OUT:
					result.setStatus(TokenAuthenticationStatus.LOGGED_OUT);
					break;
			}
		} catch (final Throwable e) {
			result.setStatus(TokenAuthenticationStatus.NOT_EXISTS);
			result.setUsername(null);
		}
		return result;
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public TokenDto refreshToken(final String identity, final String originalToken) {
		Token token = tokenRepository.findOneByDeviceIdentity(identity);

		if (token == null) {
			token = new Token();
			token.setDeviceIdentity(identity);
			token.setStatus(TokenStatus.UNAUTHENTICATED);

		} else {
			if (!token.getToken().equals(originalToken)) {
				token.setStatus(TokenStatus.UNAUTHENTICATED);
				token.setUser(null);
			}
		}

		token.setToken(UUID.randomUUID().toString());
		tokenRepository.save(token);

		return tokenConverter.convert(token);
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public void updateAccessTime(final String token) throws IException {
		tokenRepository.updateLastActiveTimestampByToken(token, new Date());
	}
}
