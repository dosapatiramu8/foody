package com.foody.common.mapper;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.MenuItemResponse;
import com.foody.data.entity.restaurant.MenuItem;
import com.foody.data.misc.Item;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuMapper {

    public MenuItem convertToMenuItem(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurantId(menuItemRequest.getRestaurantId());
        menuItem.setMenuId(menuItemRequest.getMenuId());
        menuItem.setAvailability(menuItemRequest.isAvailability());

        List<Item> itemList = menuItemRequest.getItems().stream()
                .map(this::convertToItem)
                .collect(Collectors.toList());

        menuItem.setItems(itemList);

        return menuItem;
    }

    public Item convertToItem(ItemRequest itemRequest) {
        Item item = new Item();
        BeanUtils.copyProperties(itemRequest,item);
        return item;
    }

    public ItemResponse convertToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item,itemResponse);
        return itemResponse;
    }

    public MenuItemResponse convertToMenuItemResponse(MenuItem menuItem) {
        MenuItemResponse menuItemResponse = new MenuItemResponse();
        BeanUtils.copyProperties(menuItem, menuItemResponse);
        return menuItemResponse;
    }
}
