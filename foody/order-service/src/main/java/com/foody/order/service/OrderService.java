package com.foody.order.service;

import com.foody.common.mapper.OrderMapper;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.response.order.OrderStatus;
import com.foody.common.model.response.payment.PaymentStatus;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import com.foody.data.entity.order.Order;
import com.foody.data.repository.delivery.DeliveryPartnerRepository;
import com.foody.data.repository.order.OrderRepository;
import com.foody.order.helper.OrderHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderHelper orderHelper;
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final WebClient paymentClient = WebClient.create("http://payment-service");
    private final WebClient notificationClient = WebClient.create("http://notification-service");
    private final WebClient deliveryPartnerService = WebClient.create("http://delivery-service");

    public Mono<OrderResponse> checkoutOrder(OrderRequest orderRequest) {
        return orderHelper.calculateOrderTotal(orderRequest).flatMap(order ->
                orderRepository.save(order).map(orderMapper::convertToOrderResponse));
    }
    public Mono<String> placeAnOrder(OrderRequest orderRequest) {

        if (orderRequest.getPaymentOptions().isSuccess()) {
            return orderRepository.updateOrderAndPaymentStatus(orderRequest.getOrderId(), OrderStatus.PLACED.name(), PaymentStatus.PAID.name())
                    .flatMap(orderUpdateResult -> {
                        // Asynchronously assign delivery partner and push order details to restaurant
                        Mono<Void> assignDeliveryMono = findDeliveryPartnerForOrder(orderRequest)
                                .then();

                        Mono<Void> pushOrderDetailsMono = pushOrderDetailsToRestaurant(orderRequest);

                        // Execute the asynchronous operations but don't wait for them to complete
                        Mono.when(assignDeliveryMono, pushOrderDetailsMono).subscribe();

                        return Mono.just("Order Placed successfully");
                    })
                    .onErrorResume(e -> Mono.just("Failed to process order: " + e.getMessage()));
        } else {
            return orderRepository.updateOrderAndPaymentStatus(orderRequest.getOrderId(), OrderStatus.FAILED.name(), PaymentStatus.FAILED.name())
                    .thenReturn("Order placement failed due to payment issues");
        }
    }

    //TODO: put searchDeliveryPartnersDistanceInKilometers
    private double minSearchDeliveryPartnersDistanceInKilometers = 2;
    private double maxSearchDeliveryPartnersDistanceInKilometers = 5;
    private Mono<Void> findDeliveryPartnerForOrder(OrderRequest orderRequest) {
        assignDeliveryPartner(orderRequest,0, minSearchDeliveryPartnersDistanceInKilometers);
    }


    private void assignDeliveryPartner(OrderRequest orderRequest, int attempt, double distanceInKilometers) {
        double latitude = orderRequest.getRestaurantRequest().getLatitude();
        double longitude = orderRequest.getRestaurantRequest().getLongitude();
        deliveryPartnerRepository.findAvailablePartnerNearRestaurant(latitude, longitude, distanceInKilometers)
                .flatMap(deliveryPartner -> notifyPartner(deliveryPartner, orderRequest)
                        .then(waitForAcceptance(deliveryPartner, orderRequest)))
                .timeout(Duration.ofSeconds(2), Mono.defer(() -> handleRetry(orderRequest, attempt, minSearchDeliveryPartnersDistanceInKilometers)
                        .switchIfEmpty(handleRetry(orderRequest, 0, maxSearchDeliveryPartnersDistanceInKilometers))))
                .subscribe();
    }


    private Mono<Void> notifyPartner(DeliveryPartner deliveryPartner, OrderRequest orderRequest){
        deliveryPartnerService.post().notifyPartner(deliveryPartner, orderRequest);
    }

    private Mono<DeliveryPartner> waitForAcceptance(DeliveryPartner partner, OrderRequest orderRequest) {

        return notificationService.waitForAcceptance(partner, orderRequest)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("No acceptance received"))));
    }

    private Mono<DeliveryPartner> handleRetry(OrderRequest orderRequest, int attempt, double searchDeliveryPartnersDistanceInKilometers) {
        if (attempt < 2) {
            return Mono.defer(() -> {
                assignDeliveryPartner(orderRequest, attempt + 1, searchDeliveryPartnersDistanceInKilometers);
                return Mono.empty();
            });
        } else {
            if(searchDeliveryPartnersDistanceInKilometers == maxSearchDeliveryPartnersDistanceInKilometers){
                return Mono.error(new RuntimeException("No delivery partner available."));
            } else  {
                return Mono.empty();
            }

        }
    }

    private Mono<Void> pushOrderDetailsToRestaurant(OrderRequest orderRequest) {
        // Implement the logic to push order details to the restaurant asynchronously
        // For example:
        // return restaurantService.pushOrderDetails(orderRequest);
        return Mono.fromRunnable(() -> {
            // Simulate pushing order details
            System.out.println("Pushing order details to restaurant for order: " + orderRequest.getOrderId());
        });
    }

    private void sendNotificationToDeliveryPartner(DeliveryPartner partner, Order order) {
        String topic = "delivery-partner-notifications";
        String message = "New order assigned: " + order.getId() + " to partner: " + partner.getId();
        // Here, you can include full order details in the message
        kafkaProducerService.sendMessage(topic, message);
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
