package org.trinity.yqyl.common.validation;

public interface IValidationMessage {
    public static final String PREFIX = "{VALIDATION_";
    public static final String SURFIX = "}";

    public static final String LENGTH = PREFIX + "LENGTH" + SURFIX;
    public static final String INVALID_IDENTITY_CARD_FORMAT = PREFIX + "INVALID_IDENTITY_CARD_FORMAT" + SURFIX;

}
