package com.foody.payment.controller;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.payment.PaymentResponse;
import com.foody.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public Mono<PaymentResponse> processPayment(@RequestBody OrderRequest orderRequest) {
        return paymentService.processPayment(orderRequest);
    }

    @PostMapping("/initiateRefund")
    public Mono<String> initiateRefund(@RequestParam String orderId) {
        return paymentService.initiateRefund(orderId);
    }
}

