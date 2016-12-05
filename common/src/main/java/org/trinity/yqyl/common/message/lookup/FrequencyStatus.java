package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum FrequencyStatus implements ILookupMessage<LookupType> {
    OFFEN("O"),
    SOMETIMES("S"),
    NEVER("N");

    private final String messageCode;

    private FrequencyStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.FREQUENCY_STATUS;
    }

}
