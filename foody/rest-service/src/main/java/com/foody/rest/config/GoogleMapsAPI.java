package com.foody.rest.config;

import com.foody.common.model.misc.maps.DistanceMatrixResponse;
import com.foody.common.model.misc.maps.TravelInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GoogleMapsAPI extends WebClientService{

    private WebClient webClient;

    private WebClientPropertiesConfig webClientPropertiesConfig;

    public GoogleMapsAPI(@Qualifier("mapsWebClient") WebClient webClient, WebClientPropertiesConfig webClientPropertiesConfig){
        super(webClient,webClientPropertiesConfig);
    }

    public Mono<DistanceMatrixResponse> getTravelDistanceTime(double originLat, double originLng, double destLat, double destLng) {
        HttpHeaders httpHeaders = getHttpHeaders(originLat+ "," +originLng, destLat+ "," +destLng);
        return getAsync("/maps/api/distancematrix/json", httpHeaders, DistanceMatrixResponse.class);
    }

    public Mono<TravelInfo> getTravelTimeInfo(double originLat, double originLng, double destLat, double destLng) {

        return getTravelDistanceTime(originLat, originLng, destLat, destLng).map(distanceMatrixResponse -> {
            if ("OK".equals(distanceMatrixResponse.getStatus()) &&
                    distanceMatrixResponse.getRows() != null &&
                    !distanceMatrixResponse.getRows().isEmpty() &&
                    distanceMatrixResponse.getRows().get(0).getElements() != null &&
                    !distanceMatrixResponse.getRows().get(0).getElements().isEmpty() &&
                    "OK".equals(distanceMatrixResponse.getRows().get(0).getElements().get(0).getStatus())) {

                double time = distanceMatrixResponse.getRows().get(0).getElements().get(0).getDuration().getValue();
                double distance = distanceMatrixResponse.getRows().get(0).getElements().get(0).getDistance().getValue();
                return new TravelInfo(time / 60, distance / 1000);
            } else {
                return new TravelInfo(-1, -1);
            }
        });
    }

    /**
     * Generates HttpHeaders with the Authorization token.
     *
     * @return The generated HttpHeaders object.
     */
    private HttpHeaders getHttpHeaders(String origins, String destinations) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("key", webClientPropertiesConfig.getMapsAPIKey());
        httpHeaders.add("origins", origins);
        httpHeaders.add("destinations", destinations);
        return httpHeaders;
    }

}
