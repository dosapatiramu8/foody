package com.foody.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude =
        {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class, MongoReactiveAutoConfiguration.class})
@ComponentScan({"com.foody.data", "com.foody.common"})
public class DeliveryPartnerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryPartnerApplication.class, args);
    }
}