package com.foody.delivery.service;

import reactor.core.publisher.Mono;

public interface DeliveryManagementService {
    Mono<String> assignDeliveryPartner(String orderId);
    Mono<String> getDeliveryStatus(String orderId);
}