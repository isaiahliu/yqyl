package org.trinity.yqyl.common.message.lookup;

import java.util.Locale;

import org.trinity.message.ILookupMessage;

public enum Language implements ILookupMessage<LookupType> {
    ZH_CN(Locale.SIMPLIFIED_CHINESE);
    // EN_US(Locale.US);

    private Locale locale;
    private final String messageCode;

    private Language(final Locale locale) {
        this.locale = locale;
        this.messageCode = locale.toLanguageTag();
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.LANGUAGE;
    }
}
