package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum FamilyRelationship implements ILookupMessage<LookupType> {
    SELF("SLF"),
    FATHER("FTR"),
    MOTHER("MTR"),
    GRAND_FATHER("GFT"),
    GRAND_MOTHER("GMT"),
    SON("SON"),
    DAUGHTER("DAU"),
    HUSBAND("HUS"),
    WIFE("WIF"),
    OTHER("OTH");

    private final String messageCode;

    private FamilyRelationship(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.FAMILY_RELATIONSHIP;
    }

}
