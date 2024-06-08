package com.foody.data.entity.deliverypartner;

import com.foody.common.model.misc.Location;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
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
    private DeliveryPartnerAcceptanceStatus deliveryPartnerAcceptanceStatus;
    private List<Item> items;
    private String timestamp;
}