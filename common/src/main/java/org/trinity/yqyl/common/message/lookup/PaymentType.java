package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum PaymentType implements ILookupMessage<LookupType> {
    FIXED("F"),
    TYPE("T"),
    PIECE("P"),
    PER_HOUR("H");

    private final String messageCode;

    private PaymentType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.PAYMENT_TYPE;
    }

}
