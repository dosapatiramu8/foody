package com.foody.order.helper;

import com.foody.common.exception.GlobalAppException;
import com.foody.common.mapper.ItemMapper;
import com.foody.common.model.misc.maps.TravelInfo;
import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderStatus;
import com.foody.common.model.response.order.Price;
import com.foody.data.entity.order.Order;
import com.foody.data.misc.Item;
import com.foody.rest.config.GoogleMapsAPI;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderHelper {

    //TODO: the below values should come from redis cache
    private static final double BASE_FEE = 5.0; // Base delivery fee
    private static final double DISTANCE_RATE = 1.5; // Rate per kilometer
    private static final double TIME_RATE = 0.5; // Rate per minute
    //Minimum item total price to make delivery fee zero
    private static final double MIN_ITEM_TOTAL_PRICE = 1000.0;
    private static final double GST_TAX = 0.18;
    private static final double PLATFORM_FEE = 5.0;

    private final GoogleMapsAPI googleMapsAPI;
    private final ItemMapper itemMapper;

    public Mono<Order> calculateOrderTotal(OrderRequest orderRequest) {

        AddressRequest customerAddress = prepareCustomerAddress(orderRequest);
        AddressRequest restaurantAddress = prepareRestaurantAddress(orderRequest);
        return getDistanceTime(restaurantAddress.getLatitude(), restaurantAddress.getLongitude(),
                customerAddress.getLatitude(), customerAddress.getLongitude()).flatMap(travelInfo -> {

            List<Item> items = orderRequest.getRestaurantRequest().getItemResponseList().stream()
                    .map(itemMapper::convertToItem).toList();
            double totalItemPrice = calculateTotalItemPrice(items);
            //TODO: calculate gst and need to know how to include gst
            double gst = totalItemPrice * GST_TAX;
            double deliveryFee = calculateDeliveryFee(totalItemPrice, travelInfo.getDistanceInKilometers(), travelInfo.getTimeInMinutes());

            //TODO: Include promotions discounts in next phase
            Price price = preparePrice(totalItemPrice, gst, deliveryFee);
            return prepareOrder(orderRequest, price, items, travelInfo);
        });


    }

    private static Mono<Order> prepareOrder(OrderRequest orderRequest, Price price, List<Item> items, TravelInfo travelInfo) {
        Order order = new Order();
        order.setCustomerId(orderRequest.getCustomerRequest().getCustomerId());
        order.setRestaurantId(orderRequest.getRestaurantRequest().getRestaurantId());
        order.setPrice(price);
        order.setItems(items);
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setTimeInMinutes(travelInfo.getTimeInMinutes());
        order.setDistanceInKilometers(travelInfo.getDistanceInKilometers());
        return Mono.just(order);
    }

    private static Price preparePrice(double totalItemPrice, double gst, double deliveryFee) {
        Price price = new Price();
        price.setItemsPrice(totalItemPrice);
        price.setTotalTax(gst);
        price.setPlatformFee(PLATFORM_FEE);
        price.setDeliveryCharge(deliveryFee);
        double totalPrice = totalItemPrice + gst + PLATFORM_FEE + deliveryFee;
        price.setTotalPrice(totalPrice);
        return price;
    }

    /**
     * Calculate the delivery fee based on the total item price, estimated delivery distance, and estimated delivery time.
     *
     * @param totalItemPrice The total price of the items being delivered.
     * @param estimatedDeliveryDistanceInKilometers The estimated distance for delivery in kilometers.
     * @param estimatedDeliveryTimeInMinutes The estimated delivery time in minutes.
     * @return The calculated delivery fee.
     */
    private double calculateDeliveryFee(double totalItemPrice, double estimatedDeliveryDistanceInKilometers, double estimatedDeliveryTimeInMinutes) {
        // Calculate the distance-based fee
        double distanceFee = estimatedDeliveryDistanceInKilometers * DISTANCE_RATE;

        // Calculate the time-based fee
        double timeFee = estimatedDeliveryTimeInMinutes * TIME_RATE;

        // Combine the base fee, distance fee, and time fee to get the total delivery fee
        double deliveryFee = BASE_FEE + distanceFee + timeFee;

        // Optionally, you can apply additional logic such as a minimum fee, maximum fee, or discounts
        // For example, if the total item price is above a certain threshold, you could offer free delivery
        if (totalItemPrice > MIN_ITEM_TOTAL_PRICE) {
            deliveryFee = 0;
        }

        return deliveryFee;
    }

    public static AddressRequest getPrimaryAddress(List<AddressRequest> addressRequestList, String addressNotFoundMessage) {
        return addressRequestList.stream()
                .filter(AddressRequest::isPrimary)
                .findFirst()
                .orElseThrow(() -> new GlobalAppException(HttpStatus.SC_INTERNAL_SERVER_ERROR, addressNotFoundMessage));
    }

    public static AddressRequest prepareCustomerAddress(OrderRequest orderRequest) {
        return getPrimaryAddress(orderRequest.getCustomerRequest().getAddressRequestList(), "Customer Address not found");
    }

    public static AddressRequest prepareRestaurantAddress(OrderRequest orderRequest) {
        return getPrimaryAddress(orderRequest.getRestaurantRequest().getAddressRequestList(), "Restaurant Address not found");
    }

    private static double calculateTotalItemPrice(List<Item> items) {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public Mono<TravelInfo> getDistanceTime(double originLatitude, double originLongitude,
                                            double destinationLatitude, double destinationLongitude){
        return googleMapsAPI.getTravelTimeInfo(originLatitude, originLongitude, destinationLatitude, destinationLongitude);

    }
}
