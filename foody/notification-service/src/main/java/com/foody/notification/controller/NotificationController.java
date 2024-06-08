package com.foody.notification.controller;

import com.foody.common.model.details.OrderRestaurantCustomerDeliveryDetails;
import com.foody.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/assignDeliveryPartner")
    public Mono<Void> notifyPartner(OrderRestaurantCustomerDeliveryDetails  orderRestaurantCustomerDeliveryDetails) {
        return notificationService.notifyPartner(orderRestaurantCustomerDeliveryDetails);
    }
    @PostMapping("/waitForDeliveryPartnerAcceptance")
    public Mono<Boolean> waitForAcceptance(@RequestParam String orderId) {
        return notificationService.waitForResponse(orderId);
    }

    @PostMapping("/deliveryPartnerResponse")
    public Mono<Void> deliveryPartnerResponse(@RequestParam String orderId, @RequestParam Boolean accepted) {
        return notificationService.respondToOrder(orderId, accepted).then();
    }


}
