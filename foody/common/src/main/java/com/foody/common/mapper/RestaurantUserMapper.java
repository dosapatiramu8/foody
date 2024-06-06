package com.foody.common.mapper;

import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantUserMapper {

    public Restaurant convertToRestaurantUser(RestaurantRequest restaurantUserRequest){
        Restaurant restaurantUser = new Restaurant();
        BeanUtils.copyProperties(restaurantUserRequest,restaurantUser);
        return restaurantUser;
    }

    public RestaurantResponse convertToRestaurantResponse(Restaurant restaurantUser){
        RestaurantResponse restaurantUserResponse = new RestaurantResponse();
        BeanUtils.copyProperties(restaurantUser,restaurantUserResponse);
        return restaurantUserResponse;
    }




}
