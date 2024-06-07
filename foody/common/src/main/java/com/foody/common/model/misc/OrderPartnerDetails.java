package com.foody.common.model.misc;

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
    private Restaurant restaurant;

}
