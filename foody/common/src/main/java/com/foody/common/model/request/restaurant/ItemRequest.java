package com.foody.common.model.request.restaurant;

import com.foody.common.model.details.SubItemDetails;
import com.foody.common.model.item.ItemAvailabilityDetails;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ItemRequest {

    private Integer restaurantId;
    private String itemId;
    private String unitType;
    private Double quantity;
    private ItemType itemType;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private SubItemDetails subItem;
    private BigDecimal price;
    private ItemAvailabilityDetails itemAvailability;
}
