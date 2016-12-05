package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum CredentialType implements ILookupMessage<LookupType> {
    IDENTITY_CARD("I"),
    DRIVER_LICENSE("D"),
    PASSPORT("P");

    private final String messageCode;

    private CredentialType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.CREDENTIAL_TYPE;
    }

}
