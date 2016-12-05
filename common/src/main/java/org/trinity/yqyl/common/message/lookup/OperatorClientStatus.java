package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum OperatorClientStatus implements ILookupMessage<LookupType> {
    ACTIVE("A"),
    INACTIVE("I"),
    DISABLED("D");

    private final String messageCode;

    private OperatorClientStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.OPERATOR_CLIENT_STATUS;
    }

}
