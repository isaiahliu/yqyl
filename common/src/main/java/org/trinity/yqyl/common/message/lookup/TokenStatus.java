package org.trinity.yqyl.common.message.lookup;

import org.trinity.common.accessright.TokenAuthenticationStatus;
import org.trinity.message.ILookupMessage;

public enum TokenStatus implements ILookupMessage<LookupType> {
    AUTHENTICATED("A", TokenAuthenticationStatus.AUTHENTICATED),
    EXPIRED("E", TokenAuthenticationStatus.TOKEN_IS_EXPIRED),
    UNAUTHENTICATED("U", TokenAuthenticationStatus.UNAUTHENTICATED),
    LOGGED_BY_OTHERS("L", TokenAuthenticationStatus.LOGGED_BY_OTHERS),
    PASSWORD_CHANGED("C", TokenAuthenticationStatus.PASSWORD_CHANGED),
    LOGGED_OUT("O", TokenAuthenticationStatus.LOGGED_OUT);

    private TokenAuthenticationStatus authenticationStatus;
    private final String messageCode;

    private TokenStatus(final String messageCode, final TokenAuthenticationStatus authenticationStatus) {
        this.messageCode = messageCode;
        this.authenticationStatus = authenticationStatus;
    }

    public TokenAuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.TOKEN_STATUS;
    }
}
