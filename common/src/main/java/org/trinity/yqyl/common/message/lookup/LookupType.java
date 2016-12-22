package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.GeneralMessageType;
import org.trinity.message.ILookupMessage;
import org.trinity.message.ILookupType;
import org.trinity.message.IMessageType;

public enum LookupType implements ILookupType, ILookupMessage<LookupType> {
    NA("NA", null),
    LOOKUP("LOOKUP", LookupType.class),
    ACCOUNT_STATUS("ACSTAT", AccountStatus.class),
    FAVORITE_CATEGORY("FVCATE", FavoriteCategory.class),
    ACCOUNT_POSTING_STATUS("APSTAT", AccountPostingStatus.class),
    ACCOUNT_BALANCE_STATUS("ABSTAT", AccountBalanceStatus.class),
    USER_STATUS("URSTAT", UserStatus.class),
    ACCESS_RIGHT("ACCESS", AccessRight.class),
    TOKEN_STATUS("TKSTAT", TokenStatus.class),
    SYSTEM_ATTRIBUTE_KEY("SYSKEY", SystemAttributeKey.class),
    VALUE_TYPE("VALTYP", ValueType.class),
    RECORD_STATUS("RCSTAT", RecordStatus.class),
    ACCOUNT_CATEGORY("ACCCATE", AccountCategory.class),
    COMPANY_TYPE("CMTYPE", CompanyType.class),
    OPERATOR_CLIENT_STATUS("OPCSTAT", OperatorClientStatus.class),
    ANNOUNCEMENT_STATUS("ANSTAT", AnnouncementStatus.class),
    CLIENT_TYPE("CLTYPE", ClientType.class),
    LANGUAGE("LANGUAGE", Language.class),
    MESSAGE_STATUS("MSGSTAT", MessageStatus.class),
    ORDER_STATUS("ODSTAT", OrderStatus.class),
    ORDER_OPERATION("ODOPRT", OrderOperation.class),
    SERVICE_STATUS("SVSTAT", ServiceStatus.class),
    SERVICE_SUPPLIER_CLIENT_STATUS("SSCSTAT", ServiceSupplierClientStatus.class),
    GENDER("GENDER", Gender.class),
    SERVICE_RECEIVER_CLIENT_STATUS("SRCSTAT", ServiceReceiverClientStatus.class),
    FAMILY_RELATIONSHIP("FARELTN", FamilyRelationship.class),
    FLAG_STATUS("FLSTAT", FlagStatus.class),
    SMOKER_AGE("SMKAGE", SmokerAge.class),
    FREQUENCY_STATUS("FRQSTAT", FrequencyStatus.class),
    REALNAME_STATUS("RNSTAT", RealnameStatus.class),
    CREDENTIAL_TYPE("CDTYPE", CredentialType.class),
    PAYMENT_METHOD("PMMTHD", PaymentMethod.class),
    PAYMENT_TYPE("PMTYPE", PaymentType.class),
    CITY_CODE("CTCODE", City.class),
    PROVINCE_CODE("PVCODE", Province.class),
    STAFF_STATUS("STSTAT", StaffStatus.class),
    SERVICE_ORDER_REQUIREMENT_STATUS("SORSTAT", ServiceOrderRequirementStatus.class),
    BANK("BANK", Bank.class),
    ACCOUNT_TYPE("ACCTYPE", AccountType.class),
    AUDITING_TYPE("AUDTYPE", AuditingType.class),
    TRANSACTION_TYPE("TXTYPE", TransactionType.class),
    VERIFY_CODE_TYPE("VCTYPE", VerifyCodeType.class);

    private String typeName;

    private Class<? extends ILookupMessage<LookupType>> targetType;

    private LookupType(final String typeName, final Class<? extends ILookupMessage<LookupType>> targetType) {
        this.typeName = typeName;
        this.targetType = targetType;
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

    @Override
    public Class<? extends ILookupMessage<LookupType>> getTargetType() {
        return targetType;
    }
}
