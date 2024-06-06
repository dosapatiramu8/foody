package com.foody.payment.service;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.payment.PaymentResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {

    public Mono<PaymentResponse> processPayment(OrderRequest orderRequest) {
        // Mock implementation for payment processing
        return Mono.just(new PaymentResponse("12345", true)); // Assume payment is always successful
    }
}
