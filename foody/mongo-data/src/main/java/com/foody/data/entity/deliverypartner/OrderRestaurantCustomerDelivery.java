package com.foody.data.entity.deliverypartner;

import com.foody.data.entity.maps.Location;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRestaurantCustomerDelivery {

    private String notificationId;
    private String orderId;
    private String customerId;
    private String restaurantId;
    private String deliveryPartnerId;
    private Location customerLocation;
    private Location restaurantLocation;
    private Location deliveryPartnerLocation;
    private String estimatedTime;
    private double distance;
    private String deliveryPartnerAcceptanceStatus;
    private List<Item> items;
    private String timestamp;
}