package com.foody.data.repository.order;

import com.foody.data.entity.maps.Location;
import com.foody.data.entity.order.Order;
import com.foody.data.entity.restaurant.MenuItem;
import com.foody.data.entity.restaurant.RestaurantOrder;
import com.foody.data.repository.util.LogMessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public Flux<Order> findAllOrders(Integer page, Integer size, String sortByField, Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortByField));
        return reactiveMongoTemplate.find(Query.query(buildMatchCriteria(order)).with(pageable), Order.class);
    }

    public Mono<Void> updateDeliveryPartnerDetails(Order order) {
        Query query = new Query(Criteria.where("orderId").is(order.getOrderId()));
        Update update = new Update().set("deliveryPartnerOrder", order.getDeliveryPartnerOrder());
        return Mono.fromRunnable(() -> LogMessageUtils.exec("/update delivery partner details in order DB", order,
                () -> reactiveMongoTemplate.updateFirst(query, update, Order.class)));
    }


    public Mono<Boolean> updateOrderStatus(String orderId, String orderStatus) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        Update update = new Update().set("orderStatus", orderStatus);

        return LogMessageUtils.exec("/update order status in DB", orderId + ":" + orderStatus,
                () -> reactiveMongoTemplate.updateFirst(query, update, Order.class).map(updateResult -> updateResult.getMatchedCount() > 0));
    }

    public Mono<Order> saveOrder(Order order) {
        RestaurantOrder restaurantOrder = order.getRestaurant();
        Location restaurantLocation = restaurantOrder.getAddress().getLocation();
        GeoJsonPoint geoJsonPoint = new GeoJsonPoint(restaurantLocation.getLatitude(), restaurantLocation.getLongitude());
        restaurantOrder.setLocation(geoJsonPoint);
        return LogMessageUtils.exec("/save order details in database", order, () -> reactiveMongoTemplate.save(order));
    }

    public Mono<Order> findOneOrder(String orderId) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        return LogMessageUtils.exec("/getOrderDetailsByOrderId from database", orderId,
                () -> reactiveMongoTemplate.findOne(query, Order.class));
    }

    private Criteria buildMatchCriteria(Order order) {
        Criteria criteria = new Criteria();

        // Add filters based on the fields present in the OrderRequest object
        if (order.getCustomer() != null) {
            criteria.and("customer.customerId").is(order.getCustomer().getCustomerId());
        }

        if (order.getRestaurant() != null) {
            criteria.and("restaurant.restaurantId").is(order.getRestaurant().getRestaurantId());
        }

        // Add additional filters based on other fields if needed

        return criteria;
    }

}
