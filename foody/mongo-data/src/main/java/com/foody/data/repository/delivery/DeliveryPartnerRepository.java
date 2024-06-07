package com.foody.data.repository.delivery;

import com.foody.data.entity.deliverypartner.DeliveryPartner;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeliveryPartnerRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<DeliveryPartner> save(DeliveryPartner deliveryPartnerUser) {
        return reactiveMongoTemplate.save(deliveryPartnerUser);
    }

    public Mono<DeliveryPartner> findById(String id) {
        return reactiveMongoTemplate.findById(id, DeliveryPartner.class);
    }

    public Flux<DeliveryPartner> findAll(int page, int size, String sortByField) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(new Criteria()).with(pageable), DeliveryPartner.class);
    }

    public Mono<DeliveryPartner> update(DeliveryPartner deliveryPartner) {
        return reactiveMongoTemplate.save(deliveryPartner);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), DeliveryPartner.class).then();
    }


    public Flux<DeliveryPartner> findAvailablePartnerNearRestaurant(double latitude,double longitude, double distanceInKilometers) {

        GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
        NearQuery nearQuery = NearQuery.near(location).maxDistance(distanceInKilometers).inKilometers();

        Criteria searchCriteria = new Criteria().orOperator(
                Criteria.where("availability").is(true));

        Query query = new Query(searchCriteria);

        return reactiveMongoTemplate.geoNear(nearQuery.query(query), DeliveryPartner.class)
                .map(GeoResult::getContent);

    }
}
