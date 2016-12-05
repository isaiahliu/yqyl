package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum PaymentMethod implements ILookupMessage<LookupType> {
    ONLINE("O"),
    POS("P");

    private final String messageCode;

    private PaymentMethod(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.PAYMENT_METHOD;
    }

}
