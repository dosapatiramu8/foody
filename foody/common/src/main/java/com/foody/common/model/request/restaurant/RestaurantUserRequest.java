package com.foody.common.model.request.restaurant;

import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RestaurantUserRequest extends UserRequest {
    private BigDecimal rating;
}