package com.foody.restaurant.service;

import com.foody.common.mapper.MenuMapper;
import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.misc.Item;
import com.foody.data.repository.restaurant.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    public Mono<ItemResponse> addMenu(ItemRequest itemRequest) {
        Item menuItem = menuMapper.convertToItem(itemRequest);
        return menuRepository.save(menuItem).map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<ItemResponse> updateMenu(String menuId, ItemRequest menuItemRequest) {
        return menuRepository.findById(menuId)
                .flatMap(existingMenu -> {
                    Item updatedMenu = menuMapper.convertToItem(menuItemRequest);
                    //TODO: update the field values which we get request
                    return menuRepository.save(updatedMenu);
                })
                .map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<ItemResponse> getMenuById(String menuId) {
        return menuRepository.findById(menuId).map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<Void> deleteMenu(String menuId) {
        return menuRepository.deleteById(menuId);
    }

    public Flux<ItemResponse> getAllMenus(Integer page, Integer size, String sortByField) {
        return menuRepository.findAll(page, size, sortByField).map(menuMapper::convertToMenuItemResponse);
    }
}
