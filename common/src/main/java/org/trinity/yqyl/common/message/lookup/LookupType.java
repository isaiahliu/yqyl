package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.GeneralMessageType;
import org.trinity.message.ILookupMessage;
import org.trinity.message.ILookupType;
import org.trinity.message.IMessageType;

public enum LookupType implements ILookupType, ILookupMessage<LookupType> {
    NA("NA"),
    LOOKUP("LOOKUP"),
    ACCOUNT_STATUS("ACSTAT"),
    FAVORITE_CATEGORY("FVCATE"),
    ACCOUNT_POSTING_STATUS("APSTAT"),
    ACCOUNT_BALANCE_STATUS("ABSTAT"),
    USER_STATUS("URSTAT"),
    ACCESS_RIGHT("ACCESS"),
    TOKEN_STATUS("TKSTAT"),
    SYSTEM_ATTRIBUTE_KEY("SYSKEY"),
    VALUE_TYPE("VALTYP"),
    RECORD_STATUS("RCSTAT"),
    ROLE_NAME("RLNAME"),
    ACCOUNT_CATEGORY("ACCCATE"),
    COMPANY_TYPE("CMTYPE"),
    OPERATOR_CLIENT_STATUS("OPCSTAT"),
    ANNOUNCEMENT_STATUS("ANSTAT"),
    CLIENT_TYPE("CLTYPE"),
    LANGUAGE("LANGUAGE"),
    MESSAGE_STATUS("MSGSTAT"),
    ORDER_STATUS("ODSTAT"),
    ORDER_OPERATION("ODOPRT"),
    SERVICE_STATUS("SVSTAT"),
    SERVICE_SUPPLIER_CLIENT_STATUS("SSCSTAT"),
    GENDER("GENDER"),
    SERVICE_RECEIVER_CLIENT_STATUS("SRCSTAT"),
    FAMILY_RELATIONSHIP("FARELTN"),
    FLAG_STATUS("FLSTAT"),
    SMOKER_AGE("SMKAGE"),
    FREQUENCY_STATUS("FRQSTAT"),
    REALNAME_STATUS("RNSTAT"),
    CREDENTIAL_TYPE("CDTYPE"),
    PAYMENT_METHOD("PMMTHD"),
    PAYMENT_TYPE("PMTYPE"),
    DISTRICT_CODE("DTCODE"),
    STAFF_STATUS("STSTAT"),
    SERVICE_ORDER_REQUIREMENT_STATUS("SORSTAT"),
    BANK("BANK"),
    ACCOUNT_TYPE("ACCTYPE"),
    AUDITING_TYPE("AUDTYPE"),
    TRANSACTION_TYPE("TXTYPE"),
    VERIFY_CODE_TYPE("VCTYPE");

    private String typeName;

    private LookupType(final String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String getMessageCode() {
        return typeName;
    }

    @Override
    public LookupType getMessageType() {
        return LOOKUP;
    }

    @Override
    public String getMessageTypeName() {
        return typeName;
    }

    @Override
    public IMessageType getParentType() {
        return GeneralMessageType.LOOKUP;
    }
}
