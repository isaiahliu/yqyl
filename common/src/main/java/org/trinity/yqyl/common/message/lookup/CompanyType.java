package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.ILookupMessage;

public enum CompanyType implements ILookupMessage<LookupType> {
    INCORPORATED_LIABILITY("IL"),
    PRIVATELY_RUN("PR"),
    GOVERNMENT_OWNED("GO"),
    COLLECTIVELY_RUN("CR"),
    JOINT_V_ENTURE("JE"),
    FOREIGN_FUNDED("FF"),
    OTHER("OT");

    private final String messageCode;

    private CompanyType(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.COMPANY_TYPE;
    }

}
