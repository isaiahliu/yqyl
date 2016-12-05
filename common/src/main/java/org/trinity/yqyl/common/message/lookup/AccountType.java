package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum AccountType implements ILookupMessage<LookupType> {
    PERSONAL("P"),
    BUSINESS("B");

    private final String messageCode;

    private AccountType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ACCOUNT_TYPE;
    }

}
