package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum Bank implements ILookupMessage<LookupType> {
    CBC("CBC"),
    BC("BC"),
    ABC("ABC"),
    ICBC("ICBC"),
    PBC("PBC"),
    CEB("CEB"),
    CMB("CMB"),
    CITIC("CITIC"),
    BCCB("BCCB"),
    CTB("CTB"),
    CUB("CUB"),
    HXB("HXB"),
    SPDB("SPDB"),
    CMBC("CMBC"),
    GDB("GDB");

    private final String messageCode;

    private Bank(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.BANK;
    }

}
