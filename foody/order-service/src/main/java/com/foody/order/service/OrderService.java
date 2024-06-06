package com.foody.order.service;

import com.foody.common.mapper.CustomerMapper;
import com.foody.common.mapper.OrderMapper;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.response.payment.PaymentResponse;
import com.foody.data.entity.order.Order;
import com.foody.data.repository.customer.CustomerUserRepository;
import com.foody.data.repository.order.OrderRepository;
import com.foody.order.helper.OrderHelper;
import com.foody.rest.config.GoogleMapsAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderHelper orderHelper;


    private final WebClient paymentClient = WebClient.create("http://payment-service");
    private final WebClient notificationClient = WebClient.create("http://notification-service");


    public Mono<OrderResponse> checkoutOrder(OrderRequest orderRequest) {
        return orderHelper.calculateOrderTotal(orderRequest).flatMap(order ->
                orderRepository.save(order).map(orderMapper::convertToOrderResponse));
    }
    public Mono<String> placeOrder(OrderRequest orderRequest) {
        return paymentClient.post()
                .uri("/payments/process")
                .bodyValue(orderRequest)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .flatMap(paymentResponse -> {
                    if (paymentResponse.isSuccess()) {
                        updateOrderStatus(orderRequest, "Placed");
                        return notificationClient.post()
                                .uri("/notifications/notify")
                                .bodyValue(orderRequest)
                                .retrieve()
                                .bodyToMono(Void.class)
                                .thenReturn("Order placed successfully");
                    } else {
                        return Mono.just("Payment failed, order not placed");
                    }
                });
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

    private void updateOrderStatus(OrderRequest orderRequest, String status) {
        // Mock implementation for updating order status
        System.out.println("Order status updated to: " + status);
    }
}
