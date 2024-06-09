package com.foody.restaurant.service;

import com.foody.common.mapper.ItemMapper;
import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.misc.Item;
import com.foody.data.repository.restaurant.MenuRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final ItemMapper itemMapper;

    public Mono<ItemResponse> addMenu(ItemRequest itemRequest) {
        Item menuItem = itemMapper.convertToItem(itemRequest);
        return menuRepository.save(menuItem).map(itemMapper::convertToMenuItemResponse);
    }

    public Mono<ItemResponse> updateMenu(String menuId, ItemRequest menuItemRequest) {
        return menuRepository.findById(menuId)
                .flatMap(existingMenu -> {
                    Item updatedMenu = itemMapper.convertToItem(menuItemRequest);
                    //TODO: update the field values which we get request
                    return menuRepository.save(updatedMenu);
                })
                .map(itemMapper::convertToMenuItemResponse);
    }

    public Mono<ItemResponse> getMenuById(String menuId) {
        return menuRepository.findById(menuId).map(itemMapper::convertToMenuItemResponse);
    }

    public Mono<Void> deleteMenu(String menuId) {
        return menuRepository.deleteById(menuId);
    }

    public Flux<ItemResponse> getAllMenus(Integer page, Integer size, String sortByField) {
        return menuRepository.findAll(page, size, sortByField).map(itemMapper::convertToMenuItemResponse);
    }
}
