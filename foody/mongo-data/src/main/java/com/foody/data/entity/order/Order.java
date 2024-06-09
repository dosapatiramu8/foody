package com.foody.data.entity.order;

import com.foody.data.entity.customer.CustomerOrder;
import com.foody.data.entity.deliverypartner.DeliveryPartnerOrder;
import com.foody.data.entity.price.Price;
import com.foody.data.entity.restaurant.RestaurantOrder;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    private String id;
    private String orderId;
    private CustomerOrder customer;
    private RestaurantOrder restaurant;
    private DeliveryPartnerOrder deliveryPartnerOrder;
    private List<Item> items;
    private Price price;
    private Payment payment;
    private String orderStatus;
    private double deliveryTimeInMinutes;
    private double deliveryDistanceInKilometers;

}
