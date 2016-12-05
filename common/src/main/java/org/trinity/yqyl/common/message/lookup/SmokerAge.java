package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum SmokerAge implements ILookupMessage<LookupType> {
    NEVER("N"),
    ONE_TO_FIVE("1"),
    FIVE_TO_FIFTEEN("5"),
    MORE_THAN_FIFTEEN("15");

    private final String messageCode;

    private SmokerAge(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.SMOKER_AGE;
    }

}
