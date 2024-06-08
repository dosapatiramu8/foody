package com.foody.user.controller;

import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.response.cart.CartResponse;
import com.foody.data.misc.Item;
import com.foody.user.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private final CartService cartService;

    @PostMapping("/addItemToCart")
    public Mono<CartResponse> addItemToCart(@RequestBody CartRequest cartRequest) {
        return cartService.addItemToCart(cartRequest);
    }

    @PostMapping("/clearCart")
    public Mono<String> addItemToCart(@RequestParam String userId) {
        return cartService.clearCart(userId);
    }


}
