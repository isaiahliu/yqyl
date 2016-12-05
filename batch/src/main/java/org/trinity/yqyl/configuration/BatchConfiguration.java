package org.trinity.yqyl.configuration;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.trinity.batch.configuration.InMemoryBatchConfigurer;

@Configuration
public class BatchConfiguration {
    @Bean
    public BatchConfigurer getBatchConfiguration() {
        return new InMemoryBatchConfigurer();
    }
}
