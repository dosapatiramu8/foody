package com.foody.data.repository.order;

import com.foody.data.entity.order.Order;
import com.foody.data.entity.restaurant.MenuItem;
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
public class OrderRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Order> save(Order order) {
        return reactiveMongoTemplate.save(order);
    }

    public Mono<Order> findById(String id) {
        return reactiveMongoTemplate.findById(id, Order.class);
    }

    public Flux<Order> findAll(Integer page, Integer size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), Order.class);
    }

    public Mono<Order> update(Order order) {
        return reactiveMongoTemplate.save(order);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Order.class).then();
    }

}
