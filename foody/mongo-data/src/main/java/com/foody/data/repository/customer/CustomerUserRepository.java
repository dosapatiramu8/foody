package com.foody.data.repository.customer;

import com.foody.data.entity.customer.CustomerUser;
import com.foody.data.repository.util.LogMessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class CustomerUserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<CustomerUser> save(CustomerUser customerUser){
        return LogMessageUtils.exec("save customerDetails in DB", customerUser,
                () -> reactiveMongoTemplate.insert(customerUser));
    }

    public Mono<CustomerUser> findById(String id) {
        return LogMessageUtils.exec("find customerDetails by ID in DB", id,
                () -> reactiveMongoTemplate.findById(id, CustomerUser.class));
    }


    public Flux<CustomerUser> findAll(int page, int size, String sortByField, CustomerUser customerUser) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return LogMessageUtils.exececutedFlux("find all Customer Details ", customerUser,
                () -> reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), CustomerUser.class));
    }

    public Mono<CustomerUser> update(CustomerUser customerUser) {
        return LogMessageUtils.exec("update customerDetails in DB", customerUser,
                () -> reactiveMongoTemplate.save(customerUser));
    }
    public Mono<Void> deleteById(String id) {
        return LogMessageUtils.exec("delete customerDetails by ID in DB", id,
                () -> reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), CustomerUser.class).then());
    }


}
