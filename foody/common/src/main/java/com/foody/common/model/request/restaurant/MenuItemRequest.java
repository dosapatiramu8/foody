package com.foody.common.model.request.restaurant;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MenuItemRequest {
    private Integer restaurantId;
    private Integer menuId;
    private List<ItemRequest> items;

    private boolean availability;

    //Image of items are stored in external drive naming restaurantId_menuId and accessing via URL



}
