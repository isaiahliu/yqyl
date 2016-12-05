package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum AccountStatus implements ILookupMessage<LookupType> {
    ACTIVE("A");

    private final String messageCode;

    private AccountStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ACCOUNT_STATUS;
    }

}
