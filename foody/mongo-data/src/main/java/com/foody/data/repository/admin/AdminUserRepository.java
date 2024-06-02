package com.foody.data.repository.admin;

import com.foody.data.entity.admin.AdminUser;
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
public class AdminUserRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<AdminUser> save(AdminUser adminUser) {
        return reactiveMongoTemplate.save(adminUser);
    }

    public Mono<AdminUser> findById(String id) {
        return reactiveMongoTemplate.findById(id, AdminUser.class);
    }

    public Flux<AdminUser> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), AdminUser.class);
    }

    public Mono<AdminUser> update(AdminUser adminUser) {
        return reactiveMongoTemplate.save(adminUser);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), AdminUser.class).then();
    }
}
