package com.foody.common.mapper;

import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.restaurant.Restaurant;
import com.foody.data.misc.Address;
import com.foody.data.misc.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantUserMapper {

    private final ItemMapper itemMapper;

    private final CustomerMapper customerMapper;

    public Restaurant convertToRestaurantUser(RestaurantRequest restaurantUserRequest){
        Restaurant restaurant = new Restaurant();
        BeanUtils.copyProperties(restaurantUserRequest,restaurant);
        List<Item> items = restaurantUserRequest.getItems().stream().map(itemMapper::convertToItem).toList();
        restaurant.setItems(items);
        Address address = customerMapper.convertAddressRequestToAddress(restaurantUserRequest.getAddressRequest());
        restaurant.setAddress(address);
        return restaurant;
    }

    public RestaurantResponse convertToRestaurantResponse(Restaurant restaurantUser){
        RestaurantResponse restaurantUserResponse = new RestaurantResponse();
        BeanUtils.copyProperties(restaurantUser,restaurantUserResponse);
        return restaurantUserResponse;
    }




}
