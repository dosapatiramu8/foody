package com.foody.notification.service;

import com.foody.common.model.request.order.OrderRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {

    public Mono<Void> notifyDeliveryPartner(OrderRequest orderRequest) {
        // Mock implementation for notification
        System.out.println("Notification sent to delivery partner for order: " + orderRequest);
        return Mono.empty();
    }
}
