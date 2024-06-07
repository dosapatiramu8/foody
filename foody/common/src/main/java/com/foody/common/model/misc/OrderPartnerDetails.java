package com.foody.common.model.misc;

import com.foody.common.model.response.deliverypartner.DeliveryPartnerAcceptanceStatus;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerAvailabilityStatus;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import com.foody.data.entity.restaurant.Restaurant;
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
public class OrderPartnerDetails {
    private String orderId;
    private List<Item> items;
    private String restaurantId;
    private String restaurantName;
    private String restaurantPhone;
    private String restaurantEmail;
    private double rating;
    private String deliveryPartnerId;
    private String deliveryPartnerName;
    private String deliveryPartnerPhoneNumber;
    private String deliveryPartnerEmailId;
    private DeliveryPartnerAcceptanceStatus deliveryPartnerAcceptanceStatus;
    private String currentLatitude;
    private String currentLongitude;

}
