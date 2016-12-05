package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum ServiceStatus implements ILookupMessage<LookupType> {
    ACTIVE("A"),
    OFFLINE("O"),
    DISABLED("D");

    private final String messageCode;

    private ServiceStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.SERVICE_STATUS;
    }

}
