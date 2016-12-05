package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum Gender implements ILookupMessage<LookupType> {
    MALE("M"),
    FEMALE("F");

    private final String messageCode;

    private Gender(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.GENDER;
    }

}
