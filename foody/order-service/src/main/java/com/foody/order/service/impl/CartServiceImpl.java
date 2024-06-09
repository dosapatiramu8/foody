package com.foody.order.service.impl;

import com.foody.common.mapper.CartMapper;
import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.response.cart.CartResponse;
import com.foody.data.entity.customer.Cart;
import com.foody.data.repository.customer.CartRepository;
import com.foody.order.helper.CalculateOrderPriceHelper;
import com.foody.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;

    private final CalculateOrderPriceHelper calculateOrderPriceHelper;

    private final CartMapper cartMapper;


    public Mono<CartResponse> addItemToCart(CartRequest cartRequest) {
        if (CollectionUtils.isEmpty(cartRequest.getItems())) {
            return Mono.empty();
        }
        Cart cart = cartMapper.convertToCart(cartRequest);
        cart.setUpdatedAt(LocalDateTime.now());
        return calculateOrderPriceHelper.calculateOrderPrice(cart.getItems(), cartRequest).flatMap(price -> {
            cart.setPrice(price);
            return cartRepository.addItemToCart(cart).flatMap(cartMapper::convertToCartResponse);
        });

    }

    public Mono<String> clearCart(String userId) {
        return cartRepository.clearCart(userId).thenReturn("Cart cleared successfully");
    }


}
