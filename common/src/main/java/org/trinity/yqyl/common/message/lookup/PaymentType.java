package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum PaymentType implements ILookupMessage<LookupType> {
    PER_HOUR("H"),
    PER_MONTH("M"),
    PER_WEEK("W"),
    PER_DAY("D"),
    PER_SQURED("SQ"),
    ONE_TIME("F"),
    ONE_SET("S"),
    ONE_PIECE("P"),
    ONE_STICK("ST"),
    ONE_FAN("FA"),
    ONE_BASE("BA"),
    ONE_TYPE("T"),
    ONE_BUNCH("BN"),
    ONE_SPREAD("SP"),
    ONE_CASE("CS"),
    ONE_ITEM("IT");

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
