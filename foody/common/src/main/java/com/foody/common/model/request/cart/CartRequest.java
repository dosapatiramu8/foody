package com.foody.common.model.request.cart;

import com.foody.common.model.maps.LocationDetails;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {

    private String id;
    private String userId;
    private String restaurantId;
    private String restaurantName;
    private List<Item> items;
    private LocationDetails customerLocation;
    private LocationDetails restaurantLocation;
    private double deliveryTip;

}
