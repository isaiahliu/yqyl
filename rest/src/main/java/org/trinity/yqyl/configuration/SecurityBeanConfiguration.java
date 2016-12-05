package org.trinity.yqyl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.trinity.rest.security.AbstractPreAuthenticationFilter;
import org.trinity.yqyl.rest.util.TokenFilter;

@Configuration
public class SecurityBeanConfiguration {

    @Bean
    public AbstractPreAuthenticationFilter getTokenFilter() {
        return new TokenFilter();
    }
}
