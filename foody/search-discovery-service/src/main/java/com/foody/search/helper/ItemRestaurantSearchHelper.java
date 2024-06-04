package com.foody.search.helper;

import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.common.model.response.restaurant.RestaurantItemSearchResponse;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.restaurant.Restaurant;
import com.foody.data.misc.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemRestaurantSearchHelper {

    public Mono<RestaurantItemSearchResponse> convertRestaurants(Flux<Restaurant> restaurantFlux) {
        return fetchListOfRestaurants(restaurantFlux)
                .map(restaurantResponses -> {
                    List<ItemResponse> allItems = restaurantResponses.stream()
                            .flatMap(restaurantResponse -> restaurantResponse.getItemResponseList().stream())
                            .toList();

                    RestaurantItemSearchResponse response = new RestaurantItemSearchResponse();
                    response.setRestaurants(restaurantResponses);
                    response.setItems(allItems);
                    return response;
                });
    }

    public Mono<List<RestaurantResponse>> fetchListOfRestaurants(Flux<Restaurant> restaurantFlux) {
        return restaurantFlux
                .map(this::mapToRestaurantResponse)
                .collectList();
    }


    public RestaurantResponse mapToRestaurantResponse(Restaurant restaurant) {
        List<ItemResponse> itemResponses = mapToItemResponseList(restaurant);

        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setItemResponseList(itemResponses);
        BeanUtils.copyProperties(restaurant, restaurantResponse);
        return restaurantResponse;
    }

    public List<ItemResponse> mapToItemResponseList(Restaurant restaurant) {
        return restaurant.getItems().stream()
                .map(this::mapToItemResponse)
                .toList();
    }

    public ItemResponse mapToItemResponse(Item item) {
        ItemResponse itemResponse = new ItemResponse();
        BeanUtils.copyProperties(item, itemResponse);
        return itemResponse;
    }
}
