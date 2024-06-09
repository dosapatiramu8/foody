package com.foody.common.model.details;

import com.foody.common.model.maps.LocationDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDetails {
    private String restaurantId;
    private String restaurantName;
    private String restaurantDescription;
    private String address;
    private LocationDetails restaurantLocation;

}
