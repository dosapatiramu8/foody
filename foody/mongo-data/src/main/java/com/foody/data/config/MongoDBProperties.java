package com.foody.data.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for MongoDB settings.
 * Defines properties for the MongoDB details.
 */
@Configuration
@ConfigurationProperties("mongodb")
@Data
public class MongoDBProperties {
    private String uri;
    private String databaseName;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Integer maxWaitTime;
    private Integer maxPoolSize;
    private Integer minPoolSize;
    private Integer maxConnectionIdleTime;
}
