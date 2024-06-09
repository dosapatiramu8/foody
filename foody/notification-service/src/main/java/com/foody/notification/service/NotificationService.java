package com.foody.notification.service;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.utils.AppUtils;
import com.foody.data.repository.order.OrderRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    private final OrderRepository orderRepository;

    @Value("${order.deliveryPartner.topic}")
    private String deliveryPartnerNotification;


    public Mono<String> sendOrderDeliveryPartnerNotification(String accessToken, OrderRequest orderRequest) {
        String messageBody = AppUtils.convertToString(orderRequest);
        return Mono.fromCallable(() -> {
            Message message = Message.builder().setToken(accessToken).putData("message", messageBody).build();

            return FirebaseMessaging.getInstance().send(message);
        });
    }


    public Mono<String> sendOrderToRestaurantNotification(String accessToken, OrderRequest orderRequest) {
        String messageBody = AppUtils.convertToString(orderRequest);
        return Mono.fromCallable(() -> {
            Message message = Message.builder().setToken(accessToken).putData("message", messageBody).build();

            return FirebaseMessaging.getInstance().send(message);
        });
    }


}
