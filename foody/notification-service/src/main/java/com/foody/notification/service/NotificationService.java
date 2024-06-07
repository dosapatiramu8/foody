package com.foody.notification.service;

import com.foody.common.model.misc.OrderPartnerDetails;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${order.deliveryPartner.topic}")
    private String deliveryPartnerNotification;

    private final KafkaTemplate<String, OrderPartnerDetails> kafkaTemplate;
    private final Map<String, Sinks.One<Boolean>> acceptanceSinks = new ConcurrentHashMap<>();


    public Mono<Void> notifyPartner(DeliveryPartner deliveryPartner, OrderPartnerDetails orderPartnerDetails) {
        return Mono.fromRunnable(() -> kafkaTemplate.send(deliveryPartnerNotification, deliveryPartner.getDeliveryPartnerId(),
                orderPartnerDetails)).then();
    }

    public Mono<Boolean> waitForAcceptance(DeliveryPartner deliveryPartner, OrderRequest orderRequest) {
        Sinks.One<Boolean> sink = Sinks.one();
        acceptanceSinks.put(orderRequest.getOrderId(), sink);
        return sink.asMono()
            .doFinally(signalType -> acceptanceSinks.remove(orderRequest.getOrderId()));
    }

    public void acknowledgeOrder(String orderId, boolean accepted) {
        Sinks.One<Boolean> sink = acceptanceSinks.get(orderId);
        if (sink != null) {
            sink.tryEmitValue(accepted);
        }
    }
}
