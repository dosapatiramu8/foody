package com.foody.common.model.details;

import com.foody.common.model.misc.maps.Location;
import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
import com.foody.common.model.enums.OrderStatus;
import com.foody.common.model.price.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRestaurantCustomerDeliveryDetails {

    private String notificationId;
    private String orderId;
    private String customerId;
    private DeliveryPartnerDetails deliveryPartnerDetails;
    private RestaurantDetails restaurantDetails;
    private Location customerLocation;
    private double timeInMinutes;
    private double distanceInKilometers;
    private DeliveryPartnerAcceptanceStatus deliveryPartnerAcceptanceStatus;
    private List<ItemRequest> items;
    private Price price;
    private PaymentDetails paymentDetails;
    private OrderStatus orderStatus;
    private Instant timestamp;
}