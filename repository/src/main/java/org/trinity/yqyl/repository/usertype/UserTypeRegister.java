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
import org.trinity.yqyl.common.message.lookup.City;
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
import org.trinity.yqyl.common.message.lookup.Province;
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
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AuditingType"), defaultForType = AuditingType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.City"), defaultForType = City.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Province"), defaultForType = Province.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.VerifyCodeType"), defaultForType = VerifyCodeType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.CredentialType"), defaultForType = CredentialType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.RealnameStatus"), defaultForType = RealnameStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FlagStatus"), defaultForType = FlagStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FrequencyStatus"), defaultForType = FrequencyStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.SmokerAge"), defaultForType = SmokerAge.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.RecordStatus"), defaultForType = RecordStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Gender"), defaultForType = Gender.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountBalanceStatus"), defaultForType = AccountBalanceStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountPostingStatus"), defaultForType = AccountPostingStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountStatus"), defaultForType = AccountStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AnnouncementStatus"), defaultForType = AnnouncementStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OperatorClientStatus"), defaultForType = OperatorClientStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ClientType"), defaultForType = ClientType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Language"), defaultForType = Language.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.MessageStatus"), defaultForType = MessageStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OrderStatus"), defaultForType = OrderStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.OrderOperation"), defaultForType = OrderOperation.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.Bank"), defaultForType = Bank.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountType"), defaultForType = AccountType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.CompanyType"), defaultForType = CompanyType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceStatus"), defaultForType = ServiceStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceSupplierClientStatus"), defaultForType = ServiceSupplierClientStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceReceiverClientStatus"), defaultForType = ServiceReceiverClientStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccountCategory"), defaultForType = AccountCategory.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.LookupType"), defaultForType = LookupType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.SystemAttributeKey"), defaultForType = SystemAttributeKey.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ValueType"), defaultForType = ValueType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.UserStatus"), defaultForType = UserStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.StaffStatus"), defaultForType = StaffStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FamilyRelationship"), defaultForType = FamilyRelationship.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.TokenStatus"), defaultForType = TokenStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.ServiceOrderRequirementStatus"), defaultForType = ServiceOrderRequirementStatus.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.FavoriteCategory"), defaultForType = FavoriteCategory.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.TransactionType"), defaultForType = TransactionType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.PaymentType"), defaultForType = PaymentType.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.PaymentMethod"), defaultForType = PaymentMethod.class),
        @TypeDef(typeClass = MessageUserType.class, parameters = @Parameter(name = "class", value = "org.trinity.yqyl.common.message.lookup.AccessRight"), defaultForType = AccessRight.class) })
@MappedSuperclass
public class UserTypeRegister {
}
