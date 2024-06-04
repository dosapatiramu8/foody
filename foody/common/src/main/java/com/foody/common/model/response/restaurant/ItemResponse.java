package com.foody.common.model.response.restaurant;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.request.restaurant.ItemType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ItemResponse {
    private Integer restaurantId;
    private String itemId;
    private String unitType;
    private Double quantity;
    private ItemType itemType;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private BigDecimal price;
    private boolean availability;

    //Image of items are stored in external drive naming restaurantId_itemId and accessing via URL



}
