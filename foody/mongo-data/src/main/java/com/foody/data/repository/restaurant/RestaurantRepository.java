package com.foody.data.repository.restaurant;

import com.foody.data.entity.restaurant.RestaurantUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class RestaurantRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<RestaurantUser> save(RestaurantUser restaurantUser) {
        return reactiveMongoTemplate.save(restaurantUser);
    }

    public Mono<RestaurantUser> findById(String id) {
        return reactiveMongoTemplate.findById(id, RestaurantUser.class);
    }

    public Flux<RestaurantUser> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), RestaurantUser.class);
    }

    public Mono<RestaurantUser> update(RestaurantUser restaurantUser) {
        return reactiveMongoTemplate.save(restaurantUser);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), RestaurantUser.class).then();
    }
}

