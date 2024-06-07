package com.foody.rest.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Optional;

/**
 * Configuration class for WebClientConfiguration.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebClientConfiguration implements WebFluxConfigurer {

    private final WebClientPropertiesConfig webClientPropertiesConfig;

    /**
     * Retrieves the WebClient instance configured for the partner API.
     *
     * @return The configured WebClient instance for the partner API.
     */
    @Bean(name = "mapsWebClient")
    public WebClient getMapsWebClient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key",webClientPropertiesConfig.getMapsAPIKey());
        return createCustomWebClient(webClientPropertiesConfig.getMapsWebClientHost(), httpHeaders).build();
    }


    /**
     * Retrieves the WebClient instance configured for the partner API.
     *
     * @return The configured WebClient instance for the partner API.
     */
    @Bean(name = "notificationWebClient")
    public WebClient getNotificationWebClient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key",webClientPropertiesConfig.getMapsAPIKey());
        return createCustomWebClient(webClientPropertiesConfig.getMapsWebClientHost(), httpHeaders).build();
    }

    /**
     * Creates a custom WebClient.Builder instance with the specified base URL and headers.
     *
     * @param baseUrl The base URL for the WebClient.
     * @param headers The HttpHeaders to be included as default headers in the WebClient requests.
     * @return The custom WebClient.Builder instance configured with the specified base URL and headers.
     */
    private WebClient.Builder createCustomWebClient(String baseUrl, HttpHeaders headers) {
        WebClient.Builder webClientBuilder = WebClient.builder().exchangeStrategies(getExchangeStrategies())
                .baseUrl(baseUrl);
        Optional.ofNullable(headers).ifPresent(header -> webClientBuilder.defaultHeaders(headersConsumer -> headersConsumer.addAll(headers)));
        return webClientBuilder;
    }

    /**
     * Retrieves the ExchangeStrategies used by the WebClient for handling request and response encoding.
     *
     * @return The ExchangeStrategies instance configured with the specified maxInMemorySize.
     */
    private ExchangeStrategies getExchangeStrategies() {
        return ExchangeStrategies.builder().codecs(configure -> configure.defaultCodecs().maxInMemorySize(1073741824)).build();
    }



}
