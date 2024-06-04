package com.foody.data.repository.restaurant;

import com.foody.data.entity.restaurant.MenuItem;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class MenuRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<MenuItem> save(MenuItem menuItem) {
        return reactiveMongoTemplate.save(menuItem);
    }

    public Mono<MenuItem> findById(String id) {
        return reactiveMongoTemplate.findById(id, MenuItem.class);
    }

    public Flux<MenuItem> findAll(Integer page, Integer size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), MenuItem.class);
    }

    public Mono<MenuItem> update(MenuItem menuItem) {
        return reactiveMongoTemplate.save(menuItem);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), MenuItem.class).then();
    }

    public Mono<MenuItem> addMenuItem(String restaurantId, MenuItem menuItem) {
        Query query = new Query(Criteria.where("_id").is(restaurantId));
        Update update = new Update().push("menuItems", menuItem);

        return reactiveMongoTemplate.findAndModify(query, update, MenuItem.class);
    }
}
