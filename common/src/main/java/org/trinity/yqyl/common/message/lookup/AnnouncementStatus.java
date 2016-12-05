package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum AnnouncementStatus implements ILookupMessage<LookupType> {
    ACTIVE("A"),
    YIQUAN("Y");

    private final String messageCode;

    private AnnouncementStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.ANNOUNCEMENT_STATUS;
    }

}
