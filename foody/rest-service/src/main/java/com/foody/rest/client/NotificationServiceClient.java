package com.foody.rest.client;

import com.foody.common.model.misc.OrderPartnerDetails;
import com.foody.common.model.misc.maps.DistanceMatrixResponse;
import com.foody.common.model.misc.maps.TravelInfo;
import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.rest.config.WebClientPropertiesConfig;
import com.foody.rest.config.WebClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class NotificationServiceClient extends WebClientService {

    private WebClient webClient;

    private WebClientPropertiesConfig webClientPropertiesConfig;

    public NotificationServiceClient(@Qualifier("notificationWebClient") WebClient webClient, WebClientPropertiesConfig webClientPropertiesConfig){
        super(webClient,webClientPropertiesConfig);
    }

    public Mono<Void> getTravelDistanceTime(DeliveryPartnerRequest deliveryPartnerRequest, OrderPartnerDetails orderPartnerDetails) {
        HttpHeaders httpHeaders = getHttpHeaders();
        return Mono.defer(() -> postAsync("/notification/assignDeliveryPartner", httpHeaders, null, Void.class));
    }


    /**
     * Generates HttpHeaders with the Authorization token.
     *
     * @return The generated HttpHeaders object.
     */
    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        return httpHeaders;
    }
}
