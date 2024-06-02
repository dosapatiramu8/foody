package com.foody.restaurant.service;

import com.foody.common.model.request.restaurant.RestaurantUserRequest;
import com.foody.common.model.response.restaurant.RestaurantUserResponse;
import com.foody.data.repository.restaurant.RestaurantRepository;
import com.foody.data.repository.restaurant.RestaurantUserRepository;
import com.foody.restaurant.mapper.RestaurantUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantUserMapper restaurantUserMapper;

    public Mono<RestaurantUserResponse> registerRestaurant(RestaurantUserRequest restaurantRequest) {
        return restaurantRepository.save(restaurantUserMapper.convertToRestaurantUser(restaurantRequest))
                .map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<RestaurantUserResponse> getRestaurantById(String id) {
        return restaurantRepository.findById(id).map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<RestaurantUserResponse> updateRestaurant(RestaurantUserRequest restaurantRequest) {
        return restaurantRepository.update(restaurantUserMapper.convertToRestaurantUser(restaurantRequest))
                .map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<Void> deleteRestaurant(String id) {
        return restaurantRepository.deleteById(id);
    }
}

