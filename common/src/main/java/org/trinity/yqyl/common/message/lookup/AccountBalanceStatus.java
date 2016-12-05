package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum AccountBalanceStatus implements ILookupMessage<LookupType> {
    ACTIVE("A");

    private final String messageCode;

    private AccountBalanceStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ACCOUNT_BALANCE_STATUS;
    }

}
