package com.foody.rest.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for webClient settings.
 * Defines properties for the WebClient config details.
 */
@Configuration
@ConfigurationProperties("web-flux-config")
@Data
public class WebClientPropertiesConfig {
    private Integer maxMemoryInBytes;
    private String mapsWebClientHost;
    private String mapsAPIKey;
    private String notificationWebClientHost;

}
