package org.trinity.yqyl.repository.usertype;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.trinity.repository.type.MessageUserType;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.AccountBalanceStatus;
import org.trinity.yqyl.common.message.lookup.AccountCategory;
import org.trinity.yqyl.common.message.lookup.AccountPostingStatus;
import org.trinity.yqyl.common.message.lookup.AccountStatus;
import org.trinity.yqyl.common.message.lookup.AccountType;
import org.trinity.yqyl.common.message.lookup.AnnouncementStatus;
import org.trinity.yqyl.common.message.lookup.AuditingType;
import org.trinity.yqyl.common.message.lookup.Bank;
import org.trinity.yqyl.common.message.lookup.ClientType;
import org.trinity.yqyl.common.message.lookup.CompanyType;
import org.trinity.yqyl.common.message.lookup.CredentialType;
import org.trinity.yqyl.common.message.lookup.FamilyRelationship;
import org.trinity.yqyl.common.message.lookup.FavoriteCategory;
import org.trinity.yqyl.common.message.lookup.FlagStatus;
import org.trinity.yqyl.common.message.lookup.FrequencyStatus;
import org.trinity.yqyl.common.message.lookup.Gender;
import org.trinity.yqyl.common.message.lookup.Language;
import org.trinity.yqyl.common.message.lookup.LookupType;
import org.trinity.yqyl.common.message.lookup.MessageStatus;
import org.trinity.yqyl.common.message.lookup.OperatorClientStatus;
import org.trinity.yqyl.common.message.lookup.OrderOperation;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.PaymentMethod;
import org.trinity.yqyl.common.message.lookup.PaymentType;
import org.trinity.yqyl.common.message.lookup.RealnameStatus;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus;
import org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus;
import org.trinity.yqyl.common.message.lookup.SmokerAge;
import org.trinity.yqyl.common.message.lookup.StaffStatus;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.common.message.lookup.TokenStatus;
import org.trinity.yqyl.common.message.lookup.TransactionType;
import org.trinity.yqyl.common.message.lookup.UserStatus;
import org.trinity.yqyl.common.message.lookup.ValueType;
import org.trinity.yqyl.common.message.lookup.VerifyCodeType;

@TypeDefs({
        @TypeDef(name = "AuditingType", defaultForType = AuditingType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AuditingType")),
        @TypeDef(name = "VerifyCodeType", defaultForType = VerifyCodeType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.VerifyCodeType")),
        @TypeDef(name = "CredentialType", defaultForType = CredentialType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.CredentialType")),
        @TypeDef(name = "RealnameStatus", defaultForType = RealnameStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.RealnameStatus")),
        @TypeDef(name = "FlagStatus", defaultForType = FlagStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FlagStatus")),
        @TypeDef(name = "FrequencyStatus", defaultForType = FrequencyStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FrequencyStatus")),
        @TypeDef(name = "SmokerAge", defaultForType = SmokerAge.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.SmokerAge")),
        @TypeDef(name = "RecordStatus", defaultForType = RecordStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.RecordStatus")),
        @TypeDef(name = "Gender", defaultForType = Gender.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Gender")),
        @TypeDef(name = "AccountBalanceStatus", defaultForType = AccountBalanceStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountBalanceStatus")),
        @TypeDef(name = "AccountPostingStatus", defaultForType = AccountPostingStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountPostingStatus")),
        @TypeDef(name = "AccountStatus", defaultForType = AccountStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountStatus")),
        @TypeDef(name = "AnnouncementStatus", defaultForType = AnnouncementStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AnnouncementStatus")),
        @TypeDef(name = "OperatorClientStatus", defaultForType = OperatorClientStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OperatorClientStatus")),
        @TypeDef(name = "ClientType", defaultForType = ClientType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ClientType")),
        @TypeDef(name = "Language", defaultForType = Language.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Language")),
        @TypeDef(name = "MessageStatus", defaultForType = MessageStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.MessageStatus")),
        @TypeDef(name = "OrderStatus", defaultForType = OrderStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OrderStatus")),
        @TypeDef(name = "OrderOperation", defaultForType = OrderOperation.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OrderOperation")),
        @TypeDef(name = "Bank", defaultForType = Bank.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Bank")),
        @TypeDef(name = "AccountType", defaultForType = AccountType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountType")),
        @TypeDef(name = "CompanyType", defaultForType = CompanyType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.CompanyType")),
        @TypeDef(name = "ServiceStatus", defaultForType = ServiceStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceStatus")),
        @TypeDef(name = "ServiceSupplierClientStatus", defaultForType = ServiceSupplierClientStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus")),
        @TypeDef(name = "ServiceReceiverClientStatus", defaultForType = ServiceReceiverClientStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus")),
        @TypeDef(name = "AccountCategory", defaultForType = AccountCategory.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountCategory")),
        @TypeDef(name = "LookupType", defaultForType = LookupType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.LookupType")),
        @TypeDef(name = "SystemAttributeKey", defaultForType = SystemAttributeKey.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.SystemAttributeKey")),
        @TypeDef(name = "ValueType", defaultForType = ValueType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ValueType")),
        @TypeDef(name = "UserStatus", defaultForType = UserStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.UserStatus")),
        @TypeDef(name = "StaffStatus", defaultForType = StaffStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.StaffStatus")),
        @TypeDef(name = "FamilyRelationship", defaultForType = FamilyRelationship.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FamilyRelationship")),
        @TypeDef(name = "TokenStatus", defaultForType = TokenStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.TokenStatus")),
        @TypeDef(name = "ServiceOrderRequirementStatus", defaultForType = ServiceOrderRequirementStatus.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus")),
        @TypeDef(name = "FavoriteCategory", defaultForType = FavoriteCategory.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FavoriteCategory")),
        @TypeDef(name = "TransactionType", defaultForType = TransactionType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.TransactionType")),
        @TypeDef(name = "PaymentType", defaultForType = PaymentType.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.PaymentType")),
        @TypeDef(name = "PaymentMethod", defaultForType = PaymentMethod.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.PaymentMethod")),
        @TypeDef(name = "AccessRight", defaultForType = AccessRight.class, typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccessRight")) })
@MappedSuperclass
public class UserTypeRegister {

}
