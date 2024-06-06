package com.foody.notification.controller;

import com.foody.common.model.request.order.OrderRequest;
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

    @PostMapping("/notify")
    public Mono<Void> notifyDeliveryPartner(@RequestBody OrderRequest orderRequest) {
        return notificationService.notifyDeliveryPartner(orderRequest);
    }
}
