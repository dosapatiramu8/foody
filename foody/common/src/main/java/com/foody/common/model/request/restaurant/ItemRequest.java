package com.foody.common.model.request.restaurant;

import com.foody.common.model.details.SubItem;
import com.foody.common.model.item.ItemAvailability;
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
    private SubItem subItem;
    private BigDecimal price;
    private ItemAvailability itemAvailability;
}
