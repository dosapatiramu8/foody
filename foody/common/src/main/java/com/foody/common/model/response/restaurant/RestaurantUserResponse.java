package com.foody.common.model.response.restaurant;

import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RestaurantUserResponse extends UserResponse {
    private BigDecimal rating;
}