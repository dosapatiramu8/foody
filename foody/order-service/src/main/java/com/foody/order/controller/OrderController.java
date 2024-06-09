package com.foody.order.controller;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/placeOrder")
    public Mono<ResponseEntity<String>> placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest)
                .map(result -> ResponseEntity.ok("Order placed successfully"));
    }


    @GetMapping("/{orderId}")
    public Mono<ResponseEntity<OrderResponse>> getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId)
                .map(orderResponse -> new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.OK));
    }


    @DeleteMapping("/{orderId}")
    public Mono<ResponseEntity<Boolean>> cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId).map(ResponseEntity::ok);
    }

    @GetMapping("/ordersList")
    public Mono<ResponseEntity<List<OrderResponse>>> getOrderById(@RequestParam int page, @RequestParam int size, @RequestParam String sortByField, @RequestBody OrderRequest orderRequest) {
        return orderService.getOrderList(page, size, sortByField, orderRequest)
                .map(orderResponses -> new ResponseEntity<>(orderResponses, HttpStatus.OK));
    }

    @PostMapping("/reassignOrderToAnotherPartner")
    public Mono<ResponseEntity<Void>> reassignOrderToAnotherPartner(@RequestBody OrderRequest orderRequest) {
        return orderService.reassignOrderToAnotherPartner(orderRequest)
                .thenReturn(ResponseEntity.ok().<Void>build());
    }
}
