package com.foody.data.repository.restaurant;

import com.foody.data.entity.restaurant.MenuItem;
import com.foody.data.misc.Item;
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



    public Mono<Item> findById(String id) {
        return reactiveMongoTemplate.findById(id, Item.class);
    }

    public Flux<Item> findAll(Integer page, Integer size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), Item.class);
    }

    public Mono<Item> update(Item Item) {
        return reactiveMongoTemplate.save(Item);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Item.class).then();
    }

    public Mono<Item> save(Item item) {
        Query query = new Query(Criteria.where("_id").is(item.getRestaurantId()));
        Update update = new Update().push("items", item);

        return reactiveMongoTemplate.findAndModify(query, update, Item.class);
    }
}
