package org.trinity.yqyl.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.common.locale.AbstractLocaleInterceptor;
import org.trinity.rest.util.IRestServer;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.rest.util.RestServer;
import org.trinity.rest.util.RestfulServiceUtil;
import org.trinity.rest.util.SimplifiedChineseLocaleResolver;
import org.trinity.yqyl.web.aspect.LocaleInterceptor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Value("${rest.server}")
    private String restServerUrl;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleInterceptor(getLocaleResolver())).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(3600);
    }

    @Bean
    public AbstractLocaleInterceptor getLocaleInterceptor(final LocaleResolver localeResolver) {
        return new LocaleInterceptor(localeResolver);
    }

    @Bean
    public LocaleResolver getLocaleResolver() {
        return new SimplifiedChineseLocaleResolver();
    }

    @Bean
    public IRestfulServiceUtil getRestfulServiceUtil(final IRestServer restServer, final IExceptionFactory exceptionFactory) {
        return new RestfulServiceUtil(restServer, exceptionFactory);
    }

    @Bean
    public IRestServer getRestServer() {
        return new RestServer(restServerUrl);
    }

    @Bean(name = "velocityViewResolver")
    @ConditionalOnProperty(name = "spring.velocity.enabled", matchIfMissing = true)
    public VelocityViewResolver getVelocityViewResolver(final VelocityProperties properties) {
        final VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver();
        resolver.setExposeSpringMacroHelpers(true);
        properties.applyToViewResolver(resolver);
        final String layoutUrl = properties.getProperties().getOrDefault("layoutUrl", null);
        if (layoutUrl != null) {
            resolver.setLayoutUrl(layoutUrl);
        }
        return resolver;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        final MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("5120MB");
        factory.setMaxRequestSize("5120MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public MultipartResolver multipartResolver() {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }
}
