package org.trinity.yqyl.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.trinity.common.SelfAwareProcessor;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.LocalizedException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.common.exception.factory.LocalizedExceptionFactory;
import org.trinity.common.message.MessageResolverChain;
import org.trinity.common.message.ResourceMessageResolver;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.message.IMessageResolver;
import org.trinity.message.IMessageResolverChain;
import org.trinity.message.LookupParser;
import org.trinity.message.exception.IErrorMessage;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.process.converter.LookupConverter;
import org.trinity.yqyl.common.accessright.SecurityUtil;
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

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CommonConfiguration {
    static {
        LookupParser.addEnumLookups(AuditingType.class, RecordStatus.class, Gender.class, AccountBalanceStatus.class,
                AccountPostingStatus.class, AccountStatus.class, AnnouncementStatus.class, OperatorClientStatus.class, ClientType.class,
                Language.class, MessageStatus.class, OrderStatus.class, CompanyType.class, ServiceStatus.class,
                ServiceSupplierClientStatus.class, ServiceReceiverClientStatus.class, AccountCategory.class, LookupType.class,
                SystemAttributeKey.class, ValueType.class, UserStatus.class, TokenStatus.class, FavoriteCategory.class, AccessRight.class,
                FamilyRelationship.class, FrequencyStatus.class, FlagStatus.class, SmokerAge.class, CredentialType.class,
                RealnameStatus.class, PaymentType.class, PaymentMethod.class, StaffStatus.class, ServiceOrderRequirementStatus.class,
                Bank.class, AccountType.class, OrderOperation.class, TransactionType.class, VerifyCodeType.class);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IException getException(final IMessageResolverChain messageResolver) {
        return new LocalizedException(messageResolver);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IException getException(final IMessageResolverChain messageResolver, final IErrorMessage exceptionMessage,
            final String... params) {
        return new LocalizedException(messageResolver, exceptionMessage, params);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IException getException(final IMessageResolverChain messageResolver, final String message) {
        return new LocalizedException(messageResolver, message);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IException getException(final IMessageResolverChain messageResolver, final Throwable e, final IErrorMessage exceptionMessage,
            final String... params) {
        return new LocalizedException(messageResolver, e, exceptionMessage, params);
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IException getException(final IMessageResolverChain messageResolver, final Throwable e, final String message) {
        return new LocalizedException(messageResolver, e, message);
    }

    @Bean
    public IExceptionFactory getExceptionFactory(final IMessageResolverChain messageResolver) {
        return new LocalizedExceptionFactory(messageResolver);
    }

    @Bean
    public IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> getLookupConverter(
            final IMessageResolverChain messageResolver) {
        return new LookupConverter(messageResolver);
    }

    @Bean
    public IMessageResolver getMessageResolver(final MessageSource messageResolver) {
        return new ResourceMessageResolver(messageResolver);
    }

    @Bean
    public IMessageResolverChain getMessageResolverChain(final IMessageResolver... messageResolvers) {
        return new MessageResolverChain(messageResolvers);
    }

    @Bean
    public ISecurityUtil<AccessRight> getSecurityUtil(final IMessageResolverChain messageResolver,
            final IExceptionFactory exceptionFactory) {
        return new SecurityUtil(messageResolver, exceptionFactory);
    }

    @Bean
    public SelfAwareProcessor getSelfAwareProcessor() {
        return new SelfAwareProcessor();
    }

    @Bean
    public LocalValidatorFactoryBean getValidator(final MessageSource messageSource) {
        final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }
}
