package com.foody.restaurant.controller;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.common.model.response.restaurant.MenuItemResponse;
import com.foody.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addItem")
    public Mono<ItemResponse> addItem(@RequestBody ItemRequest menuItemRequest) {
        return menuService.addMenu(menuItemRequest);
    }

    @PutMapping("/menu/{menuId}")
    public Mono<ItemResponse> updateMenu(@PathVariable String menuId, @RequestBody ItemRequest menuItemRequest) {
        return menuService.updateMenu(menuId, menuItemRequest);
    }

    @GetMapping("/menu/{menuId}")
    public Mono<ItemResponse> getMenuById(@PathVariable String menuId) {
        return menuService.getMenuById(menuId);
    }

    @DeleteMapping("/menu/{menuId}")
    public Mono<Void> deleteMenu(@PathVariable String menuId) {
        return menuService.deleteMenu(menuId);
    }

    @GetMapping("/all")
    public Flux<ItemResponse> getAllMenus(@RequestParam Integer page, @RequestParam Integer size, String sortByField) {
        return menuService.getAllMenus(page,size,sortByField);
    }
}
