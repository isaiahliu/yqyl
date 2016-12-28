package org.trinity.yqyl.configuration;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.trinity.batch.configuration.InMemoryBatchConfigurer;

@Configuration
public class BatchConfiguration {
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public BatchConfigurer getBatchConfiguration() {
        return new InMemoryBatchConfigurer(resourceLoader);
    }
}
