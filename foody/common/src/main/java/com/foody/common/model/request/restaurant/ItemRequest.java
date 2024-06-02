package com.foody.common.model.request.restaurant;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ItemRequest {

    private String itemId;
    private String unitType;
    private Double quantity;
    private ItemType itemType;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private BigDecimal price;
}
