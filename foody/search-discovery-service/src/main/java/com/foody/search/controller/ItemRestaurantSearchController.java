package com.foody.search.controller;

import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.common.model.response.restaurant.RestaurantItemSearchResponse;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.restaurant.Restaurant;
import com.foody.search.service.ItemRestaurantSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemRestaurantSearchController {

    private final ItemRestaurantSearchService itemRestaurantSearchService;

    @GetMapping("/searchRestaurantItems")
    public Mono<RestaurantItemSearchResponse> searchItemRestaurant(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String searchValue){
        return itemRestaurantSearchService.searchItemRestaurant(latitude,longitude,searchValue);
    }

    @GetMapping("/restaurantsByItem")
    public Mono<List<RestaurantResponse>> getRestaurantsByItemName(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String itemName) {
        return itemRestaurantSearchService.getRestaurantsByItemName(latitude, longitude, itemName);
    }

    @GetMapping("/restaurantsByRestaurant")
    public Mono<List<ItemResponse>> getRestaurantsByRestaurantName(@RequestParam String restaurantId) {
        return itemRestaurantSearchService.getItemsByRestaurantName(restaurantId);
    }
}
