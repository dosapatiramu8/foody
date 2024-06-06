package com.foody.data.repository.restaurant;

import com.foody.data.entity.restaurant.Restaurant;
import com.foody.data.entity.restaurant.RestaurantUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class RestaurantRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Restaurant> save(Restaurant restaurantUser) {
        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(restaurantUser.getLongitude(),restaurantUser.getLatitude());
        restaurantUser.setLocation(geoJsonPoint);
        return reactiveMongoTemplate.save(restaurantUser);
    }

    public Mono<Restaurant> findById(String id) {
        return reactiveMongoTemplate.findById(id, Restaurant.class);
    }

    public Flux<Restaurant> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), Restaurant.class);
    }

    public Mono<Restaurant> update(Restaurant restaurantUser) {
        return reactiveMongoTemplate.save(restaurantUser);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Restaurant.class).then();
    }
}

