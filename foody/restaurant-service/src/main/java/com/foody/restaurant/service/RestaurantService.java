package com.foody.restaurant.service;

import com.foody.common.mapper.RestaurantUserMapper;
import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.repository.restaurant.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantUserMapper restaurantUserMapper;

    public Mono<RestaurantResponse> registerRestaurant(RestaurantRequest restaurantRequest) {
        return restaurantRepository.save(restaurantUserMapper.convertToRestaurantUser(restaurantRequest))
                .map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<RestaurantResponse> getRestaurantById(String id) {
        return restaurantRepository.findById(id).map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<RestaurantResponse> updateRestaurant(RestaurantRequest restaurantRequest) {
        return restaurantRepository.update(restaurantUserMapper.convertToRestaurantUser(restaurantRequest))
                .map(restaurantUserMapper::convertToRestaurantResponse);
    }

    public Mono<Void> deleteRestaurant(String id) {
        return restaurantRepository.deleteById(id);
    }
}

