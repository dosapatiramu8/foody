package com.foody.restaurant.controller;

import com.foody.common.model.request.restaurant.RestaurantUserRequest;
import com.foody.common.model.response.restaurant.RestaurantUserResponse;
import com.foody.restaurant.service.RestaurantUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RestaurantUserController {


    private final RestaurantUserService restaurantUserService;

    // Create operation
    @PostMapping
    public Mono<RestaurantUserResponse> createRestaurantUser(@RequestBody RestaurantUserRequest request) {
        return restaurantUserService.createRestaurantUser(request);
    }

    // Read operation
    /*@GetMapping("/{id}")
    public Mono<RestaurantUserResponse> getRestaurantUserById(@PathVariable("id") int id) {
        return restaurantUserService.getRestaurantUserById(id);
    }

    // Update operation
    @PutMapping("/{id}")
    public Mono<RestaurantUserResponse> updateRestaurantUser(@PathVariable("id") int id, @RequestBody RestaurantUserRequest request) {
        return restaurantUserService.updateRestaurantUser(id, request);
    }

    // Delete operation
    @DeleteMapping("/{id}")
    public Mono<Void> deleteRestaurantUser(@PathVariable("id") int id) {
        return restaurantUserService.deleteRestaurantUser(id);
    }*/
}

