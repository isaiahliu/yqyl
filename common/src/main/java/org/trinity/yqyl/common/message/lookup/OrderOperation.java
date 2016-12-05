package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum OrderOperation implements ILookupMessage<LookupType> {
    PROPOSAL("10"),
    EDIT_BEFORE_PROCESSING("15"),
    TAKEN("20"),
    ASSIGNMENT("30"),
    PROCESSING("40"),
    CHANGE_PRICE("45"),
    PAYED("50"),
    RECEIPT_UPLOADED("55"),
    APPRAISED("60"),
    REPLYED("65"),
    SETTLED("100"),
    CANCEL_REQUEST("105"),
    RECEIVER_CANCELLED("110"),
    SUPPLIER_CANCELLED("115"),
    ADMIN_CANCELLED("116"),
    CANCEL_REJECTED("120");

    private final String messageCode;

    private OrderOperation(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ORDER_OPERATION;
    }

}
