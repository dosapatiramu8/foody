package com.foody.data.entity.restaurant;

import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

    private Integer restaurantId;
    private Integer menuId;
    private List<Item> items;
    private boolean availability;
}
