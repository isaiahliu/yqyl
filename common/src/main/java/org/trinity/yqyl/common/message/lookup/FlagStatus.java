package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum FlagStatus implements ILookupMessage<LookupType> {
    YES("Y"),
    NO("N");

    private final String messageCode;

    private FlagStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.FLAG_STATUS;
    }

}
