package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum TransactionType implements ILookupMessage<LookupType> {
    ORDER_PAYMENT("ORDER"),
    TOP_UP("TOPUP"),
    CLAIM("CLAIM"),
    BIND("BIND");

    private final String messageCode;

    private TransactionType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.TRANSACTION_TYPE;
    }

}
