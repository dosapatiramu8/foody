package com.foody.notification.controller;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/assignDeliveryPartner")
    public Mono<String> assignDeliveryPartner(@RequestBody OrderRequest orderRequest) {
        return notificationService.sendOrderDeliveryPartnerNotification(
                orderRequest.getDeliveryPartnerOrder().getDeviceAccessToken(), orderRequest);
    }


    @PostMapping("/sendOrderDetailsToRestaurant/{accessToken}")
    public Mono<String> assignOrderToRestaurantPartner(@RequestBody OrderRequest orderRequest) {
        return notificationService.sendOrderToRestaurantNotification(
                orderRequest.getRestaurant().getDeviceAccessToken(), orderRequest);
    }

}
