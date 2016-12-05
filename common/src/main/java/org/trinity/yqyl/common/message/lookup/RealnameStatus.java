package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum RealnameStatus implements ILookupMessage<LookupType> {
    VERIFIED("V"),
    DENIED("D"),
    NOT_READY("N"),
    INPROGRESS("I");

    private final String messageCode;

    private RealnameStatus(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.REALNAME_STATUS;
    }

}
