package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum SystemAttributeKey implements ILookupMessage<LookupType> {
    TOKEN_EXPIRE_DAYS("TKEXPDAYS", "30"),
    VERIFY_CODE_EXPIRE_MINUTES("VCEXPMINS", "30"),
    MAX_SERVICE_INFO_IMAGES("MAXSIIMG", "6"),
    ABOUT_US("ABOUTUS", "");

    private final String messageCode;
    private String defaultValue;

    private SystemAttributeKey(final String messageCode, final String defaultValue) {
        this.messageCode = messageCode;
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.SYSTEM_ATTRIBUTE_KEY;
    }
}
