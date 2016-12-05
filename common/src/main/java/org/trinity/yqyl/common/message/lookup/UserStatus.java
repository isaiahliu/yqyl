package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum UserStatus implements ILookupMessage<LookupType> {
    UNREGISTERED("U"),
    ACTIVE("A"),
    DESTROYED("D");

    private final String messageCode;

    private UserStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.USER_STATUS;
    }

}
