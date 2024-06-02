package com.foody.data.repository.customer;

import com.foody.data.entity.customer.CustomerUser;
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
public class CustomerUserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<CustomerUser> save(CustomerUser customerUser){
        return reactiveMongoTemplate.save(customerUser);
    }

    public Mono<CustomerUser> findById(String id) {
        return reactiveMongoTemplate.findById(id, CustomerUser.class);
    }

    public Flux<CustomerUser> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), CustomerUser.class);
    }

    public Mono<CustomerUser> update(CustomerUser customerUser) {
        return reactiveMongoTemplate.save(customerUser);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), CustomerUser.class).then();
    }

}
