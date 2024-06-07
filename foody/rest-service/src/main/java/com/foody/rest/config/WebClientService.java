package com.foody.rest.config;


import com.foody.common.exception.GlobalAppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Slf4j
public class WebClientService {


    private final WebClient webClient;


    private final WebClientPropertiesConfig webClientPropertiesConfig;


    /**
     * Performs a POST request with the provided URL, HTTP headers, payload, and returns a Mono of Response expected type.
     *
     * @param uri                        The URL for the POST request.
     * @param httpHeaders                The HTTP headers for the POST request.
     * @param parameterizedTypeReference The parameterized type reference representing the desired response type.
     * @param payload                    The payload to be included in the POST request body.
     * @param <P>                        The type of the payload.
     * @param <R>                        The type of the expected response.
     * @return A Mono of ResponseEntity representing the response entity with status, headers, and body.
     */
    public <P, R> Mono<R> postAsync(String uri, HttpHeaders httpHeaders, P payload, ParameterizedTypeReference<R> parameterizedTypeReference) {
        long startTime = System.currentTimeMillis();
        return postCall(uri,httpHeaders,payload).bodyToMono(parameterizedTypeReference).doOnSuccess(response -> {
                    log.info("Client API call {} {} ",uri, payload);
            })
        .onErrorMap(
            WebClientResponseException.class,
            e -> new GlobalAppException(e.getStatusCode().value(),
                e.getResponseBodyAsString()));
    }

    /**
     * Performs a POST request with the provided URL, HTTP headers, payload, and returns a Mono of Response expected type.
     *
     * @param uri         The URL for the POST request.
     * @param httpHeaders The HTTP headers for the POST request.
     * @param payload     The payload to be included in the POST request body.
     * @param <P>         The type of the payload.
     * @param <R>         The type of the expected response.
     * @return A Mono of ResponseEntity representing the response entity with status, headers, and body.
     */
    public <P, R> Mono<R> postAsync(String uri, HttpHeaders httpHeaders, P payload, Class<R> r) {
        long startTime = System.currentTimeMillis();
        return postCall(uri,httpHeaders,payload).bodyToMono(r).doOnSuccess(response -> {
            log.info("Client API call {} {} {} ",uri, httpHeaders, payload);
        }).onErrorMap(
            WebClientResponseException.class,
            e -> new GlobalAppException(e.getStatusCode().value(),
                e.getResponseBodyAsString()));
    }


    /**
     * Performs a GET request with the provided URL, HTTP headers, payload, and returns a Mono of expected Response Type.
     *
     * @param uri         The URL for the POST request.
     * @param httpHeaders The HTTP headers for the POST request.
     * @param <R>         The type of the expected response.
     * @return A Mono of ResponseEntity representing the response entity with status, headers, and body.
     */
    public <R> Mono<R> getAsync(String uri, HttpHeaders httpHeaders, Class<R> r) {

        return getCallToMono(uri,httpHeaders,r).doOnSuccess(response -> {
            log.info("Client API call {} ",uri);
        }).onErrorMap(
            WebClientResponseException.class,
            e -> new GlobalAppException(e.getStatusCode().value(),
                e.getResponseBodyAsString()));
    }




    /**
     * Performs an HTTP POST request.
     *
     * @param uri         The URL to which the request is sent.
     * @param httpHeaders The HttpHeaders object containing any headers to be included in the request.
     * @param payload     The payload data to be sent with the request.
     * @param <P>         The type of the payload.
     * @return A WebClient.ResponseSpec object representing the response.
     */
    private <P> WebClient.ResponseSpec postCall(String uri, HttpHeaders httpHeaders, P payload) {
        return webClient.post().uri(uri)
            .headers(headers -> headers.addAll(httpHeaders))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve();
    }
    /**
     * Performs an asynchronous HTTP GET request.
     *
     * @param uri         The URL to which the request is sent.
     * @param httpHeaders The HttpHeaders object containing any headers to be included in the request.
     * @return A WebClient.ResponseSpec object representing the response.
     */
    private WebClient.ResponseSpec getAsyncCall(String uri,HttpHeaders httpHeaders) {
        return webClient.get().uri(uri).headers(headers -> headers.addAll(httpHeaders))
            .retrieve();

    }

    /**
     * Performs an asynchronous HTTP GET request and maps the response body to a Mono.
     *
     * @param uri         The URL to which the request is sent.
     * @param httpHeaders The HttpHeaders object containing any headers to be included in the request.
     * @param r           The Class representing the type to which the response body should be mapped.
     * @param <R>         The type of the response body.
     * @return A Mono object representing the response body.
     */
    private <R> Mono<R> getCallToMono(String uri,HttpHeaders httpHeaders, Class<R> r) {
        return getAsyncCall(uri,httpHeaders).bodyToMono(r).onErrorMap(
                WebClientResponseException.class,
                e -> new GlobalAppException(e.getStatusCode().value(),
                        e.getResponseBodyAsString()));
    }



    public <R> Mono<R> getAsyncWithoutHeaders(String uri, Class<R> r) {
        long startTime = System.currentTimeMillis();
        return getCallToMonoWithoutHeaders(uri,r).doOnSuccess(response -> {
            log.info("Client API call {}",uri);
        });
    }


    private <R> Mono<R> getCallToMonoWithoutHeaders(String uri, Class<R> r) {
        return getAsyncCallWithoutHeader(uri).bodyToMono(r).onErrorMap(
                WebClientResponseException.class,
                e -> new GlobalAppException(e.getStatusCode().value(),
                        e.getResponseBodyAsString()));
    }


    private WebClient.ResponseSpec getAsyncCallWithoutHeader(String uri) {
        return webClient.get().uri(uri)
                .retrieve();

    }
}
