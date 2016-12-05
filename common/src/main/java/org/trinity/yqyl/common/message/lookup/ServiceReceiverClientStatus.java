package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum ServiceReceiverClientStatus implements ILookupMessage<LookupType> {
    PROPOSAL("P"),
    REALNAME("R"),
    DISABLED("D");

    private final String messageCode;

    private ServiceReceiverClientStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.SERVICE_RECEIVER_CLIENT_STATUS;
    }

}
