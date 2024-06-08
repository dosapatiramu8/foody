package com.foody.data.repository.customer;

import com.foody.data.entity.customer.Cart;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CartRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Void> clearCart(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        return reactiveMongoTemplate.remove(query).then();
    }

    public Mono<Cart> addItemToCart(Cart cart) {
       return reactiveMongoTemplate.save(cart);
    }


}
