package com.foody.common.model.request.order;

import com.foody.common.model.details.PaymentDetails;
import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.data.entity.price.Price;
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
    private CustomerRequest customerRequest;
    private RestaurantRequest restaurantRequest;
    private List<Item> items;
    private Price price;
    private PaymentDetails paymentDetails;
    private double deliveryTimeInMinutes;
    private double deliveryDistanceInKilometers;

}
