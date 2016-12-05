package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum ValueType implements ILookupMessage<LookupType> {
    STRING("S"),
    DATE("D"),
    TIME("T"),
    TIMESTAMP("DT"),
    NUMBER("N");

    private final String messageCode;

    private ValueType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.VALUE_TYPE;
    }

}
