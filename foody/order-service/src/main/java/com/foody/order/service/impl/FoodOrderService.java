package com.foody.order.service.impl;

import com.foody.common.mapper.OrderMapper;
import com.foody.common.model.details.OrderRestaurantCustomerDeliveryDetails;
import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.cart.CartResponse;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.enums.OrderStatus;
import com.foody.common.model.response.payment.PaymentStatus;
import com.foody.common.utils.AppUtils;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import com.foody.data.entity.deliverypartner.DeliveryPartnerOrder;
import com.foody.data.entity.maps.Location;
import com.foody.data.entity.order.Order;
import com.foody.data.repository.customer.CartRepository;
import com.foody.data.repository.delivery.DeliveryPartnerRepository;
import com.foody.data.repository.order.OrderRepository;
import com.foody.order.helper.OrderHelper;
import com.foody.order.service.OrderService;
import com.foody.rest.client.NotificationServiceClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class FoodOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderMapper orderMapper;
    private final OrderHelper orderHelper;
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final NotificationServiceClient notificationServiceClient;
    private final Jedis jedis;
    //TODO: move to constants
    private static final String DELIVERY_PARTNERS_KEY = "delivery_partners";
    private static final String ROUND_ROBIN_INDEX_KEY = "round_robin_index";
    private final WebClient paymentClient = WebClient.create("http://payment-service");

    private final WebClient deliveryPartnerService = WebClient.create("http://delivery-service");


    @Override
    public Mono<OrderResponse> getOrderById(String orderId) {
        return orderRepository.findOneOrder(orderId)
                .map(orderMapper::convertToOrderResponse);
    }

    public Mono<Boolean> cancelOrder(String orderId) {
        //TODO: Initiate refund and change the order to cancel
        return orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELED.name()); // Dummy implementation
    }

    @Override
    public Mono<List<OrderResponse>> getOrderList(int page, int size, String sortByField, OrderRequest orderRequest) {
        return orderRepository.findAllOrders(page, size, sortByField, orderMapper.convertToOrder(orderRequest))
                .map(orderMapper::convertToOrderResponse) // Convert each order to OrderResponse
                .collectList();
    }

    @Override
    public Mono<String> reassignOrderToAnotherPartner(OrderRequest orderRequest) {

        return pushOrderDetailsToDeliveryPartner(orderRequest);
    }


    public Mono<String> placeOrder(OrderRequest orderRequest) {

        if (orderRequest.getPaymentDetails().getPaymentStatus().name().equals(PaymentStatus.PAID.name())) {
            Order order = orderMapper.convertToOrder(orderRequest);
            order.setOrderStatus(OrderStatus.PLACED.name());
            return orderRepository.saveOrder(order).flatMap(orderUpdateResult -> {

                // Asynchronously assign delivery partner and push order details to restaurant
                Mono<String> assignDeliveryMono = pushOrderDetailsToDeliveryPartner(orderRequest);

                Mono<Void> pushOrderDetailsMono = pushOrderDetailsToRestaurant(orderRequest);
                // Execute the asynchronous operations but don't wait for them to complete
                Mono.when(assignDeliveryMono, pushOrderDetailsMono).subscribe();

                return Mono.just("Order Placed successfully");
            }).onErrorResume(e -> Mono.just("Failed to process order: " + e.getMessage()));
        }else {
            return Mono.just("Order Failed");
        }
    }

    //TODO: put searchDeliveryPartnersDistanceInKilometers
    private double minSearchDeliveryPartnersDistanceInKilometers = 2;
    private double maxSearchDeliveryPartnersDistanceInKilometers = 5;


    //List contains deliveryPartnerId:deliveryPartnerName:deliveryPartnerPhoneNumber
    public String findNearbyPartners(double lat, double lon) {
        String deliveryPartner = fetchPartnersFromRedis(lat, lon, minSearchDeliveryPartnersDistanceInKilometers);
        if(StringUtils.isEmpty(deliveryPartner)){
            return fetchPartnersFromRedis(lat,lon,  maxSearchDeliveryPartnersDistanceInKilometers);
        }
        return deliveryPartner;
    }

    private String fetchPartnersFromRedis(double lat, double lon, double radius) {
        List<GeoRadiusResponse> responses =  jedis.georadius(DELIVERY_PARTNERS_KEY, lon, lat, radius, GeoUnit.KM);
        if (!responses.isEmpty()) {
            // Get the current round-robin index
            long index = jedis.incr(ROUND_ROBIN_INDEX_KEY) - 1;
            // Select the delivery partner based on round-robin index
            return responses.get((int) (index % responses.size())).getMemberByString();
        }
        return Strings.EMPTY;
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


    private Mono<String> pushOrderDetailsToDeliveryPartner(OrderRequest orderRequest) {
        Location restaurantLocation = orderRequest.getRestaurant().getAddress().getLocation();
        String deliveryPartnerDetails = findNearbyPartners(restaurantLocation.getLatitude(), restaurantLocation.getLongitude());
        DeliveryPartnerOrder deliveryPartnerOrder = AppUtils.convertToObjectType(deliveryPartnerDetails, DeliveryPartnerOrder.class);
        orderRequest.setDeliveryPartnerOrder(deliveryPartnerOrder);
        return notificationServiceClient.sendDeliveryPartnerNotification(orderRequest).thenReturn("Assigned Delivery Partner");
    }


}
