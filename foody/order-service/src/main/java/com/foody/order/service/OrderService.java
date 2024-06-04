package com.foody.order.service;

import com.foody.common.mapper.CustomerMapper;
import com.foody.common.mapper.OrderMapper;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.data.entity.order.Order;
import com.foody.data.repository.customer.CustomerUserRepository;
import com.foody.data.repository.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;


    public Mono<OrderResponse> checkOut(OrderRequest orderRequest) {
        Order order = orderMapper.convertToOrder(orderRequest);
        return Mono.empty(); // Dummy implementation
    }
    public Mono<String> placeOrder(OrderRequest orderRequest) {
        // Implement logic to place an order
        return Mono.empty(); // Dummy implementation
    }

    public Mono<OrderResponse> getOrderById(String orderId) {
        // Implement logic to retrieve an order by ID
        return Mono.empty(); // Dummy implementation
    }

    public Mono<OrderResponse> updateOrder(String orderId, OrderRequest orderRequest) {
        // Implement logic to update an order
        return Mono.empty(); // Dummy implementation
    }

    public Mono<Void> cancelOrder(String orderId) {
        // Implement logic to cancel an order
        return Mono.empty(); // Dummy implementation
    }
}
