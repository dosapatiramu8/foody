package com.foody.common.model.response.restaurant;

import com.foody.common.model.details.SubItemDetails;
import com.foody.common.model.item.ItemAvailabilityDetails;
import com.foody.common.model.request.restaurant.ItemType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ItemResponse {
    private Integer restaurantId;
    private String itemId;
    private String unitType;
    private double quantity;
    private ItemType itemType;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private SubItemDetails subItem;
    private double price;
    private double centralGst;
    private double stateGst;
    private ItemAvailabilityDetails itemAvailability;

    //Image of items are stored in external drive naming restaurantId_itemId and accessing via URL



}
