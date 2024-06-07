package com.foody.data.repository.restaurant;

import com.foody.data.entity.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ItemRestaurantSearchRepository {


    private final ReactiveMongoTemplate reactiveMongoTemplate;


    public Flux<Restaurant> findByLocationAndNameOrItemNear(double latitude, double longitude,
                                                            double maxDistanceInKilometers, String searchQuery) {
        GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
        NearQuery nearQuery = NearQuery.near(location).maxDistance(maxDistanceInKilometers).inKilometers();

        Criteria searchCriteria = new Criteria().orOperator(
                Criteria.where("restaurantName").regex(searchQuery, "i"),
                Criteria.where("items.name").regex(searchQuery, "i"),
                Criteria.where("items.description").regex(searchQuery, "i")
        );

        Query query = new Query(searchCriteria);

        return reactiveMongoTemplate.geoNear(nearQuery.query(query), Restaurant.class)
                .map(GeoResult::getContent);
    }

    public Flux<Restaurant> findByLocationItemName(double latitude, double longitude, double maxDistanceInKilometers, String itemName) {
        GeoJsonPoint location = new GeoJsonPoint(longitude, latitude);
        NearQuery nearQuery = NearQuery.near(location).maxDistance(maxDistanceInKilometers).inKilometers();

        Criteria searchCriteria = new Criteria().orOperator(
                Criteria.where("items.name").regex(itemName, "i"),
                Criteria.where("items.description").regex(itemName, "i")
        );

        Query query = new Query(searchCriteria);

        return reactiveMongoTemplate.geoNear(nearQuery.query(query), Restaurant.class)
                .map(GeoResult::getContent);
    }

    public Mono<Restaurant> findByLocationRestaurantName(String restaurantId) {

        Criteria searchCriteria = new Criteria().orOperator(
                Criteria.where("restaurantId")
        );

        Query query = new Query(searchCriteria);

        return reactiveMongoTemplate.findOne(query,Restaurant.class);
    }
}
