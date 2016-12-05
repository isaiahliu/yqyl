package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum StaffStatus implements ILookupMessage<LookupType> {
    ACTIVE("A"),
    ON_LEAVE("L"),
    FIRED("F");

    private final String messageCode;

    private StaffStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.STAFF_STATUS;
    }

}
