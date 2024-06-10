package com.foody.common.model.request.restaurant;

import com.foody.common.model.enums.RestaurantAvailabilityStatus;
import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
public class RestaurantRequest{
    private UserRequest userRequest;
    private String restaurantId;
    private String restaurantName;
    private String restaurantDescription;
    private Instant openTime;
    private Instant closeTime;
    private AddressRequest addressRequest;
    private List<ItemRequest> items;
    private RestaurantAvailabilityStatus restaurantAvailabilityStatus;
}