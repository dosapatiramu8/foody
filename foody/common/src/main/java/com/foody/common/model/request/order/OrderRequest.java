package com.foody.common.model.request.order;

import com.foody.common.model.details.PaymentDetails;
import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.data.entity.customer.CustomerOrder;
import com.foody.data.entity.deliverypartner.DeliveryPartnerOrder;
import com.foody.data.entity.price.Price;
import com.foody.data.entity.restaurant.RestaurantOrder;
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
public class OrderRequest {
    private String orderId;
    private CustomerOrder customer;
    private RestaurantOrder restaurant;
    private DeliveryPartnerOrder deliveryPartnerOrder;
    private List<Item> items;
    private Price price;
    private PaymentDetails paymentDetails;
    private double deliveryTimeInMinutes;
    private double deliveryDistanceInKilometers;

}
