package com.foody.order.service;

import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.response.cart.CartResponse;
import reactor.core.publisher.Mono;

public interface CartService {

    // This method is used to update/delete item in cart that means it replaces cart collection
    Mono<CartResponse> addItemToCart(CartRequest cartRequest);
    Mono<String> clearCart(String userId);
}
