package com.foody.restaurant.controller;

import com.foody.common.model.request.restaurant.RestaurantUserRequest;
import com.foody.common.model.response.restaurant.RestaurantUserResponse;
import com.foody.restaurant.service.RestaurantUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant-users")
public class RestaurantUserController {

    @Autowired
    private RestaurantUserService restaurantUserService;

    // Create operation
    @PostMapping
    public RestaurantUserResponse createRestaurantUser(@RequestBody RestaurantUserRequest request) {
        return restaurantUserService.createRestaurantUser(request);
    }

    // Read operation
    @GetMapping("/{id}")
    public RestaurantUserResponse getRestaurantUserById(@PathVariable("id") int id) {
        return restaurantUserService.getRestaurantUserById(id);
    }

    // Update operation
    @PutMapping("/{id}")
    public RestaurantUserResponse updateRestaurantUser(@PathVariable("id") int id, @RequestBody RestaurantUserRequest request) {
        return restaurantUserService.updateRestaurantUser(id, request);
    }

    // Delete operation
    @DeleteMapping("/{id}")
    public void deleteRestaurantUser(@PathVariable("id") int id) {
        restaurantUserService.deleteRestaurantUser(id);
    }
}


