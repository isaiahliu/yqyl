package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum OrderStatus implements ILookupMessage<LookupType> {
    REQUEST_GRABBED("G"),
    UNPROCESSED("U"),
    IN_PROGRESS("I"),
    AWAITING_PAYMENT("A"),
    AWAITING_APPRAISE("P"),
    REQUEST_FAILED("F"),
    SETTLED("S"),
    CANCELLED("C"),
    CANCEL_REQUESTED("Q");

    private final String messageCode;

    private OrderStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ORDER_STATUS;
    }

}
