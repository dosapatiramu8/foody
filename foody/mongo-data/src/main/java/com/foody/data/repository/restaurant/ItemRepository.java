package com.foody.data.repository.restaurant;

import com.foody.data.misc.Item;
import com.mongodb.client.result.UpdateResult;
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

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class ItemRepository {

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

    public Flux<Item> saveAll(List<Item> items) {
        return reactiveMongoTemplate.insertAll(items)
                .thenMany(Flux.fromIterable(items));
    }

    public Mono<Boolean> updateItem(Item item) {
        Query query = new Query(Criteria.where("itemId").is(item.getItemId()));
        Update update = new Update();
        update.addToSet("itemName", item.getItemName());
        if(Objects.nonNull(item.getItemAvailability())){
            update.addToSet("itemAvailability.from",item.getItemAvailability().getFrom());
        }
        return reactiveMongoTemplate.updateFirst(query, update, Item.class).map(updateResult -> updateResult.getMatchedCount() > 0);
    }
}
