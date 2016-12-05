package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum AuditingType implements ILookupMessage<LookupType> {
    PROPOSAL("P"),
    PROPOSAL_UPDATE("U"),
    APPLY("A"),
    REJECT("R"),
    UPDATE_REGULAR_INFO("G");

    private final String messageCode;

    private AuditingType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.AUDITING_TYPE;
    }

}
