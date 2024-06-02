package com.foody.data.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableReactiveMongoRepositories
@RequiredArgsConstructor
@Slf4j

public class MongoDBConfiguration extends AbstractReactiveMongoConfiguration {

    private final MongoDBProperties mongoDBProperties;

    @Bean
    public ReactiveMongoOperations reactiveMongoOperations(ReactiveMongoTemplate reactiveMongoTemplate) {
        return reactiveMongoTemplate;
    }

    /**
     * Retrieves a ReactiveMongoClient instance configured with the settings from the MongoDBConfig.
     *
     * @return The configured MongoClient for reactive MongoDB operations.
     */
    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(MongoClientSettings.builder().retryReads(true).retryWrites(true)
                .applyToSocketSettings(builder -> builder.connectTimeout(mongoDBProperties.getConnectTimeout(), TimeUnit.SECONDS).readTimeout(mongoDBProperties.getReadTimeout(), TimeUnit.SECONDS))
                .applyConnectionString(new ConnectionString(mongoDBProperties.getUri())).applyToConnectionPoolSettings(builder ->
                    builder.maxWaitTime(mongoDBProperties.getMaxWaitTime(), TimeUnit.SECONDS).maxSize(mongoDBProperties.getMaxPoolSize()).minSize(mongoDBProperties.getMinPoolSize())
                            .maxConnectionIdleTime(mongoDBProperties.getMaxConnectionIdleTime(), TimeUnit.SECONDS)
                ).build());
    }

    /**
     * Retrieves the name of the MongoDB database to be used for this application.
     *
     * @return The name of the MongoDB database.
     */
    @Override
    protected String getDatabaseName() {
        return mongoDBProperties.getDatabaseName();
    }
}
