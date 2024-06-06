package com.foody.data.entity.order;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.order.OrderStatus;
import com.foody.common.model.response.order.Price;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<Item> items;
    private Price price;
    private OrderStatus orderStatus;
    private double timeInMinutes;
    private double distanceInKilometers;
}
