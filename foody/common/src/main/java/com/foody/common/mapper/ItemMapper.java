package com.foody.common.mapper;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.entity.restaurant.MenuItem;
import com.foody.data.misc.Item;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {


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

    public ItemResponse convertToMenuItemResponse(Item menuItem) {
        ItemResponse menuItemResponse = new ItemResponse();
        BeanUtils.copyProperties(menuItem, menuItemResponse);
        return menuItemResponse;
    }
}
