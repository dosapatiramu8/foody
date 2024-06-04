package com.foody.common.mapper;

import com.foody.common.model.request.restaurant.RestaurantUserRequest;
import com.foody.common.model.response.restaurant.RestaurantUserResponse;
import com.foody.data.entity.restaurant.RestaurantUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantUserMapper {

    public RestaurantUser convertToRestaurantUser(RestaurantUserRequest restaurantUserRequest){
        RestaurantUser restaurantUser = new RestaurantUser();
        BeanUtils.copyProperties(restaurantUserRequest,restaurantUser);
        return restaurantUser;
    }

    public RestaurantUserResponse convertToRestaurantResponse(RestaurantUser restaurantUser){
        RestaurantUserResponse restaurantUserResponse = new RestaurantUserResponse();
        BeanUtils.copyProperties(restaurantUser,restaurantUserResponse);
        return restaurantUserResponse;
    }




}
