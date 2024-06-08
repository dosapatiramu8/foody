package com.foody.notification.service;

import com.foody.common.model.details.OrderRestaurantCustomerDeliveryDetails;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;


    @Value("${order.deliveryPartner.topic}")
    private String deliveryPartnerNotification;



    public void sendNotificationOrderToDeliveryPartner(OrderRestaurantCustomerDeliveryDetails orderRestaurantCustomerDeliveryDetails) {
        redisTemplate.convertAndSend(topic.getTopic(), orderRestaurantCustomerDeliveryDetails);
    }

    public Mono<Boolean> waitForResponse(String orderId) {
        Sinks.One<Boolean> sink = Sinks.one();
        String sinkKey = "sink:" + orderId;
        redisTemplate.opsForValue().set(sinkKey, DeliveryPartnerAcceptanceStatus.PENDING.name()); // Set initial status as PENDING with TTL

        // Poll Redis for response status
        return Mono.fromCallable(() -> {
                    String status = (String) redisTemplate.opsForValue().get(sinkKey);
                    return DeliveryPartnerAcceptanceStatus.ACCEPTED.name().equals(status);
                })
                .repeatWhenEmpty(repeat -> repeat.delayElements(Duration.ofSeconds(1)).take(60)) // Poll every second for up to 60 seconds
                .timeout(Duration.ofMinutes(1))
                .doFinally(signalType -> redisTemplate.delete(sinkKey));
    }


    /**
     *
     * This method is called when a response (acceptance or rejection) is received from a delivery partner.
     * It retrieves the associated sink from Redis using the order ID and emits the provided response status
     * to complete the Mono that is waiting for this response.
     *
     * @param orderId  the ID of the order for which the response is received
     * @param accepted the response status from the delivery partner; true if accepted, false if rejected
     */
    public Mono<Void> respondToOrder(String orderId, boolean accepted) {
        String sinkKey = "sink:" + orderId;
        redisTemplate.opsForValue().set(sinkKey, accepted ? DeliveryPartnerAcceptanceStatus.ACCEPTED.name() : DeliveryPartnerAcceptanceStatus.REJECTED.name());
        return Mono.empty();
    }



}
