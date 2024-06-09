package com.foody.order.service;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {

    //Once order is placed clear cart
    Mono<String> placeOrder(OrderRequest orderRequest);
    Mono<OrderResponse> getOrderById(String orderId);
    //cancel order and initiate refund if already paid
    Mono<Boolean> cancelOrder(String orderId);
    Mono<List<OrderResponse>> getOrderList(int page, int size, String sortByField, OrderRequest orderRequest);
    Mono<String> reassignOrderToAnotherPartner(OrderRequest orderRequest);
}