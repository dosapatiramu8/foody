package com.foody.restaurant.service;

import com.foody.common.model.request.restaurant.RestaurantUserRequest;
import com.foody.common.model.response.restaurant.RestaurantUserResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantUserService {

    private List<RestaurantUser> restaurantUsers = new ArrayList<>();
    private int nextId = 1;

    public RestaurantUserResponse createRestaurantUser(RestaurantUserRequest request) {
        int id = nextId++;
        RestaurantUser newUser = new RestaurantUser(id, request.getName(), request.getEmail());
        restaurantUsers.add(newUser);
        return new RestaurantUserResponse(newUser.getId(), newUser.getName(), newUser.getEmail());
    }

    public RestaurantUserResponse getRestaurantUserById(int id) {
        Optional<RestaurantUser> optionalUser = restaurantUsers.stream().filter(u -> u.getId() == id).findFirst();
        if (optionalUser.isPresent()) {
            RestaurantUser user = optionalUser.get();
            return new RestaurantUserResponse(user.getId(), user.getName(), user.getEmail());
        } else {
            throw new IllegalArgumentException("Restaurant User not found with ID: " + id);
        }
    }

    public RestaurantUserResponse updateRestaurantUser(int id, RestaurantUserRequest request) {
        Optional<RestaurantUser> optionalUser = restaurantUsers.stream().filter(u -> u.getId() == id).findFirst();
        if (optionalUser.isPresent()) {
            RestaurantUser user = optionalUser.get();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            return new RestaurantUserResponse(user.getId(), user.getName(), user.getEmail());
        } else {
            throw new IllegalArgumentException("Restaurant User not found with ID: " + id);
        }
    }

    public void deleteRestaurantUser(int id) {
        boolean removed = restaurantUsers.removeIf(u -> u.getId() == id);
        if (!removed) {
            throw new IllegalArgumentException("Restaurant User not found with ID: " + id);
        }
    }
}
