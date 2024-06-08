package com.foody.order.service;

import com.foody.common.mapper.OrderMapper;
import com.foody.common.model.details.OrderRestaurantCustomerDeliveryDetails;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.enums.OrderStatus;
import com.foody.common.model.response.payment.PaymentStatus;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import com.foody.data.entity.order.Order;
import com.foody.data.repository.customer.CartRepository;
import com.foody.data.repository.delivery.DeliveryPartnerRepository;
import com.foody.data.repository.order.OrderRepository;
import com.foody.order.helper.OrderHelper;
import com.foody.rest.client.NotificationServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderHelper orderHelper;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final NotificationServiceClient notificationServiceClient;
    private final Jedis jedis;
    //TODO: move to constants
    private static final String DELIVERY_PARTNERS_KEY = "delivery_partners";
    private final WebClient paymentClient = WebClient.create("http://payment-service");

    private final WebClient deliveryPartnerService = WebClient.create("http://delivery-service");



    public Mono<String> placeAnOrder(OrderRequest orderRequest) {

        if (orderRequest.getPaymentDetails().getPaymentStatus().name().equals(PaymentStatus.PAID.name())) {
            Order order = orderMapper.convertToOrder(orderRequest);
            order.setOrderStatus(OrderStatus.PLACED.name());
            return orderRepository.saveOrder(order).flatMap(orderUpdateResult -> {

                // Asynchronously assign delivery partner and push order details to restaurant
                Mono<Void> assignDeliveryMono = findDeliveryPartnerForOrder(orderUpdateResult).then();

                Mono<Void> pushOrderDetailsMono = pushOrderDetailsToRestaurant(orderRequest);
                // Execute the asynchronous operations but don't wait for them to complete
                Mono.when(assignDeliveryMono, pushOrderDetailsMono).subscribe();

                return Mono.just("Order Placed successfully");
            }).onErrorResume(e -> Mono.just("Failed to process order: " + e.getMessage()));
        } else {
            return orderRepository.updateOrderAndPaymentStatus(orderRequest.getOrderId(), OrderStatus.FAILED.name(), PaymentStatus.FAILED.name())
                    .thenReturn("Order placement failed due to payment issues");
        }
    }

    //TODO: put searchDeliveryPartnersDistanceInKilometers
    private double minSearchDeliveryPartnersDistanceInKilometers = 2;
    private double maxSearchDeliveryPartnersDistanceInKilometers = 5;



    public List<String> findNearbyPartners(double lat, double lon, double radius) {
        return jedis.georadius(DELIVERY_PARTNERS_KEY, lon, lat, radius, GeoUnit.KM);
    }



    private void assignDeliveryPartner(OrderRequest orderRequest, int attempt, double distanceInKilometers) {
        double latitude = orderRequest.getRestaurantRequest().getLatitude();
        double longitude = orderRequest.getRestaurantRequest().getLongitude();

        deliveryPartnerRepository.findAvailablePartnerNearRestaurant(latitude, longitude, distanceInKilometers).flatMap(deliveryPartner -> {
            OrderRestaurantCustomerDeliveryDetails orderRestaurantCustomerDeliveryDetails = orderMapper.convertToOrderRestaurantCustomerDeliveryDetails(deliveryPartner, orderRequest);

            notificationServiceClient.sendDeliveryPartnerNotification(orderRestaurantCustomerDeliveryDetails);
            return notificationServiceClient.waitForResponse(orderRequest.getOrderId()).flatMap(accepted -> {
                if (accepted) {
                    // Update order status to ACCEPTED and return
                    return deliveryPartnerRepository.updateOrderStatus(orderRequest.getOrderId(), DeliveryPartnerAcceptanceStatus.ACCEPTED);
                } else {
                    // If not accepted, try next partner
                    return handleRetry(orderRequest, attempt, distanceInKilometers);
                }
            });
        }).timeout(Duration.ofSeconds(2)).onErrorResume(throwable -> handleRetry(orderRequest, attempt, distanceInKilometers)).subscribe();
    }


    public void handleResponse(String orderId, boolean accepted) {
        notificationServiceClient.respondToOrder(orderId, accepted);
    }


    private Mono<Void> notifyPartner(DeliveryPartner deliveryPartner, OrderRequest orderRequest) {
        deliveryPartnerService.post().notifyPartner(deliveryPartner, orderRequest);
    }

    private Mono<DeliveryPartner> waitForAcceptance(DeliveryPartner partner, OrderRequest orderRequest) {

        return notificationService.waitForAcceptance(partner, orderRequest).switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("No acceptance received"))));
    }

    private Mono<DeliveryPartner> handleRetry(OrderRequest orderRequest, int attempt, double searchDeliveryPartnersDistanceInKilometers) {
        if (attempt < 2) {
            return Mono.defer(() -> {
                assignDeliveryPartner(orderRequest, attempt + 1, searchDeliveryPartnersDistanceInKilometers);
                return Mono.empty();
            });
        } else {
            if (searchDeliveryPartnersDistanceInKilometers == maxSearchDeliveryPartnersDistanceInKilometers) {
                return Mono.error(new RuntimeException("No delivery partner available."));
            } else {
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
