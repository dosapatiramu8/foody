package com.foody.common.model.request.order;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.data.entity.customer.CustomerUser;
import com.foody.data.entity.restaurant.RestaurantUser;
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
    private CustomerUser customerUser;
    private RestaurantUser restaurantUser;
    private List<ItemRequest> items;
}
