package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum VerifyCodeType implements ILookupMessage<LookupType> {
    REGISTER("RG"),
    FORGET_PASSWORD("FP");

    private final String messageCode;

    private VerifyCodeType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.VERIFY_CODE_TYPE;
    }

}
