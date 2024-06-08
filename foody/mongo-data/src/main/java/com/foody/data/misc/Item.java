package com.foody.data.misc;

import com.foody.common.model.details.SubItem;
import com.foody.common.model.item.ItemAvailability;
import com.foody.common.model.price.Taxes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private Taxes itemTaxes;
    private List<SubItem> subItem;
    private ItemAvailability itemAvailability;
}
