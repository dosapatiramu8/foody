package com.foody.common.model.response.order;

import com.foody.common.model.enums.OrderStatus;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.entity.price.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<ItemResponse> items;
    private Price price;
    private OrderStatus status;
    private double timeInMinutes;
    private double distanceInKilometers;
    // Add more fields as needed
}
