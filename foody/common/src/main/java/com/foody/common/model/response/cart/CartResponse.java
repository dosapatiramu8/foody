package com.foody.common.model.response.cart;

import com.foody.common.model.misc.maps.TravelInfo;
import com.foody.common.model.price.Price;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private String userId;
    private String restaurantId;
    private String restaurantName;
    private List<Item> items;
    private Price price;
    private TravelInfo deliveryTravelInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
