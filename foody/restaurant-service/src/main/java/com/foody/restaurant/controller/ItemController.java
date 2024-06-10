package com.foody.restaurant.controller;

import com.foody.common.model.request.restaurant.ItemRequest;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.restaurant.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item/addItem")
    public Mono<ResponseEntity<List<ItemResponse>>> addItem(@RequestBody List<ItemRequest> itemRequests) {
        return itemService.saveItems(itemRequests).map(itemResponses -> new ResponseEntity<>(itemResponses, HttpStatus.OK));
    }

    @PutMapping("/item/itemId")
    public Mono<ResponseEntity<Boolean>> updateMenu(@PathVariable String itemId, @RequestBody ItemRequest menuItemRequest) {
        return itemService.updateItem(itemId, menuItemRequest).map(itemResponse -> new ResponseEntity<>(itemResponse, HttpStatus.OK));
    }

    @GetMapping("/item/{itemId}")
    public Mono<ResponseEntity<ItemResponse>> getMenuById(@PathVariable String itemId) {
        return itemService.getItemById(itemId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/item/{itemId}")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String itemId) {
        return itemService.deleteItem(itemId).thenReturn(ResponseEntity.ok().<Void>build());
    }

    @GetMapping("/item/all")
    public Mono<ResponseEntity<Flux<ItemResponse>>> getAllMenus(@RequestParam Integer page, @RequestParam Integer size, @RequestParam String sortByField) {
        Flux<ItemResponse> itemResponses = itemService.getAllItems(page, size, sortByField);
        return Mono.just(ResponseEntity.ok(itemResponses));
    }
}
