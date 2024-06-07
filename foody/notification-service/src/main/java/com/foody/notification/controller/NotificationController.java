package com.foody.notification.controller;

import com.foody.common.model.misc.OrderPartnerDetails;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import com.foody.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notify")
    public Mono<Void> notifyDeliveryPartner(@RequestBody OrderRequest orderRequest) {
        return notificationService.notifyDeliveryPartner(orderRequest);
    }

    @PostMapping("/notifyPartner")
    public Mono<Void> notifyPartner(DeliveryPartner deliveryPartner, OrderPartnerDetails orderPartnerDetails) {
        return notificationService.notifyPartner(deliveryPartner, orderPartnerDetails);
    }
    @PostMapping("/notifyPartner")
    public Mono<Boolean> waitForAcceptance(DeliveryPartner deliveryPartner, OrderRequest orderRequest) {
        return notificationService.waitForAcceptance(deliveryPartner, orderRequest);
    }
    @PostMapping("/notifyPartner")
    public void acknowledgeOrder(String orderId, boolean accepted) {
        notificationService.acknowledgeOrder(orderId,accepted);
    }

}
