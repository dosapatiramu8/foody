package com.foody.data.repository.delivery;

import com.foody.data.entity.deliverypartner.DeliveryPartnerUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeliveryPartnerUserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<DeliveryPartnerUser> save(DeliveryPartnerUser deliveryPartnerUser) {
        return reactiveMongoTemplate.save(deliveryPartnerUser);
    }

    public Mono<DeliveryPartnerUser> findById(String id) {
        return reactiveMongoTemplate.findById(id, DeliveryPartnerUser.class);
    }

    public Flux<DeliveryPartnerUser> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), DeliveryPartnerUser.class);
    }

    public Mono<DeliveryPartnerUser> update(DeliveryPartnerUser deliveryPartnerUser) {
        return reactiveMongoTemplate.save(deliveryPartnerUser);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), DeliveryPartnerUser.class).then();
    }
}
