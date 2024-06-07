package com.foody.order.controller;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkoutOrder")
    public Mono<OrderResponse> checkoutOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.checkoutOrder(orderRequest);
    }

    /*@PostMapping("/api/orders/placeOrder")
    public Mono<ResponseEntity<Map<String, String>>> placeOrder(@RequestBody OrderRequest orderRequest, UriComponentsBuilder uriBuilder) {

        orderService.createOrder(orderRequest, uriBuilder)
                .thenReturn(ResponseEntity.ok(Map.of("paymentUrl", paymentUrl)));


        return orderService.createPaymentUrl(orderRequest, callbackUrl)
                .flatMap(paymentUrl -> {
                    // Save the order with a PENDING status
                    return orderService.saveOrder(orderRequest, orderId, "PENDING")
                            .thenReturn(ResponseEntity.ok(Map.of("paymentUrl", paymentUrl)));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(Map.of("error", e.getMessage()))));
    }*/


    @PostMapping("/placeOrder")
    public Mono<ResponseEntity<String>> placeAnOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeAnOrder(orderRequest)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(e.getMessage())));
    }

    @GetMapping("/{orderId}")
    public Mono<OrderResponse> getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public Mono<OrderResponse> updateOrder(@PathVariable String orderId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orderId, orderRequest);
    }

    @DeleteMapping("/{orderId}")
    public Mono<Void> cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }
}
