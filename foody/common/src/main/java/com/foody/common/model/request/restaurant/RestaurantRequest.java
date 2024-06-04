package com.foody.common.model.request.restaurant;

import com.foody.common.model.request.misc.UserRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RestaurantRequest extends UserRequest {
    private String restaurantName;
    private String restaurantDescription;
    private List<ItemResponse> itemResponseList;
}