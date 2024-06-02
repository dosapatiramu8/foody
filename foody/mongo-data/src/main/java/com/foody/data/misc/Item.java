package com.foody.data.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String itemId;
    private String unitType;
    private Double quantity;
    private String itemName;
    private String itemDescription;
    private String itemCategoryCode;
    private String itemCategoryDescription;
    private BigDecimal price;
}
