package com.foody.common.model.response.restaurant;

import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RestaurantResponse extends UserResponse {
    private String restaurantName;
    private String restaurantDescription;
    private List<ItemResponse> itemResponseList;
    private BigDecimal rating;
}