package com.foody.restaurant.service;

import com.foody.common.mapper.MenuMapper;
import com.foody.common.model.response.restaurant.MenuItemResponse;
import com.foody.data.entity.restaurant.MenuItem;
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

    public Mono<MenuItemResponse> addMenu(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuMapper.convertToMenuItem(menuItemRequest);
        return menuRepository.save(menuItem).map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<MenuItemResponse> updateMenu(String menuId, MenuItemRequest menuItemRequest) {
        return menuRepository.findById(menuId)
                .flatMap(existingMenu -> {
                    MenuItem updatedMenu = menuMapper.convertToMenuItem(menuItemRequest);
                    //TODO: update the field values which we get request
                    return menuRepository.save(updatedMenu);
                })
                .map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<MenuItemResponse> getMenuById(String menuId) {
        return menuRepository.findById(menuId).map(menuMapper::convertToMenuItemResponse);
    }

    public Mono<Void> deleteMenu(String menuId) {
        return menuRepository.deleteById(menuId);
    }

    public Flux<MenuItemResponse> getAllMenus(Integer page, Integer size, String sortByField) {
        return menuRepository.findAll(page, size, sortByField).map(menuMapper::convertToMenuItemResponse);
    }
}
