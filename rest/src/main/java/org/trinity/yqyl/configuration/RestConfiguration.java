package org.trinity.yqyl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.trinity.common.locale.AbstractLocaleInterceptor;
import org.trinity.rest.util.SimplifiedChineseLocaleResolver;
import org.trinity.yqyl.rest.aspect.LocaleInterceptor;

@Configuration
@EnableWebMvc
public class RestConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(getLocaleInterceptor(getLocaleResolver())).addPathPatterns("/**");
    }

    @Bean
    public AbstractLocaleInterceptor getLocaleInterceptor(final LocaleResolver localeResolver) {
        return new LocaleInterceptor(localeResolver);
    }

    @Bean
    public LocaleResolver getLocaleResolver() {
        return new SimplifiedChineseLocaleResolver();
    }
}
