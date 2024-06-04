package com.foody.search.service;

import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.common.model.response.restaurant.RestaurantItemSearchResponse;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.restaurant.Restaurant;
import com.foody.data.repository.restaurant.ItemRestaurantSearchRepository;
import com.foody.search.helper.ItemRestaurantSearchHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRestaurantSearchService {

    private final ItemRestaurantSearchRepository itemRestaurantSearchRepository;

    private final ItemRestaurantSearchHelper itemRestaurantSearchHelper;

    @Value("${maxDistanceInKilometers}")
    private Double maxDistanceInKilometers;

    public Mono<RestaurantItemSearchResponse> searchItemRestaurant(double latitude, double longitude, String searchValue){
        Flux<Restaurant> restaurantFlux = itemRestaurantSearchRepository.findByLocationAndNameOrItemNear(latitude,longitude,maxDistanceInKilometers,searchValue);
        return itemRestaurantSearchHelper.convertRestaurants(restaurantFlux);
    }


    public Mono<List<RestaurantResponse>> getRestaurantsByItemName(double latitude, double longitude, String itemName) {
        Flux<Restaurant> restaurantFlux = itemRestaurantSearchRepository.findByLocationItemName(latitude, longitude, maxDistanceInKilometers,itemName);
        return itemRestaurantSearchHelper.fetchListOfRestaurants(restaurantFlux);
    }

    public Mono<List<ItemResponse>> getItemsByRestaurantName(String restaurantId) {
        Mono<Restaurant> restaurantMono = itemRestaurantSearchRepository.findByLocationRestaurantName(restaurantId);
        return restaurantMono.map(itemRestaurantSearchHelper::mapToItemResponseList);
    }
}


