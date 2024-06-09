package com.foody.common.mapper;

import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.cart.CartResponse;
import com.foody.common.model.response.restaurant.RestaurantResponse;
import com.foody.data.entity.customer.Cart;
import com.foody.data.entity.restaurant.Restaurant;
import com.foody.data.entity.restaurant.RestaurantOrder;
import com.foody.data.misc.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final ItemMapper itemMapper;

    public Cart convertToCart(CartRequest cartRequest) {

        Cart cart = new Cart();
        BeanUtils.copyProperties(cartRequest, cart);
        cart.setItems(cartRequest.getItems());

        return cart;
    }

    public Mono<CartResponse> convertToCartResponse(Cart cart){
        CartResponse cartResponse = new CartResponse();
        BeanUtils.copyProperties(cart, cartResponse);
        cartResponse.setPrice(cart.getPrice());
        cartResponse.setItems(cart.getItems());
        return Mono.just(cartResponse);
    }

    

}
