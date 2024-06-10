package com.foody.restaurant.controller;

import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RestaurantController {


    private final RestaurantService restaurantService;

    // Create operation
    @PostMapping("/")
    public Mono<RestaurantResponse> registerRestaurant(@RequestBody RestaurantRequest restaurantUserRequest) {
        return restaurantService.registerRestaurant(restaurantUserRequest);
    }

    // Read operation
    @GetMapping("/{id}")
    public Mono<RestaurantResponse> getRestaurantUserById(@PathVariable("id") String id) {
        return restaurantService.getRestaurantById(id);
    }

    // Update operation
    @PutMapping("/update")
    public Mono<RestaurantResponse> updateRestaurantUser(@RequestBody RestaurantRequest request) {
        return restaurantService.updateRestaurant(request);
    }

    // Delete operation
    @DeleteMapping("/{id}")
    public Mono<Void> deleteRestaurantUser(@PathVariable("id") String id) {
        return restaurantService.deleteRestaurant(id);
    }


}

