package com.foody.common.model.request.restaurant;

import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class RestaurantRequest extends UserRequest {
    private String restaurantId;
    private String restaurantName;
    private String restaurantDescription;
    private Instant openTime;
    private Instant closeTime;
    private AddressRequest addressRequest;
    private List<ItemRequest> items;
}