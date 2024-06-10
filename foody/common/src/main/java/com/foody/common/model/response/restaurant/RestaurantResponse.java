package com.foody.common.model.response.restaurant;

import com.foody.common.model.enums.RestaurantAvailabilityStatus;
import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
public class RestaurantResponse {
    private UserResponse userResponse;
    private String restaurantName;
    private String restaurantDescription;
    private List<ItemResponse> itemResponseList;
    private RestaurantAvailabilityStatus restaurantAvailabilityStatus;
    private BigDecimal rating;
}