package com.foody.data.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String restaurantId;
    private String itemId;
    private String unitType;
    private String unitSize;  // E.g., "1 piece", "250g", "500ml", etc.
    private String unitDescription;  // Additional description if needed
    private double quantity;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private double price;
}
