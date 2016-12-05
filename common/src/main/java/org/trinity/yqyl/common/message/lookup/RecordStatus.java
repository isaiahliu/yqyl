package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum RecordStatus implements ILookupMessage<LookupType> {
    ACTIVE("A"),
    DISABLED("D");

    private final String messageCode;

    private RecordStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.RECORD_STATUS;
    }

}
