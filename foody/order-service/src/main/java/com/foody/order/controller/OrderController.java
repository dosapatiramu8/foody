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
