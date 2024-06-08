package com.foody.user.service;

import com.foody.common.mapper.CartMapper;
import com.foody.common.model.misc.maps.TravelInfo;
import com.foody.common.model.request.cart.CartRequest;
import com.foody.common.model.response.cart.CartResponse;
import com.foody.data.entity.customer.Cart;
import com.foody.data.entity.price.DeliveryFee;
import com.foody.data.entity.price.Price;
import com.foody.data.entity.price.Taxes;
import com.foody.data.misc.Item;
import com.foody.data.repository.customer.CartRepository;
import com.foody.rest.client.GoogleMapsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepository cartRepository;

    private final CartMapper cartMapper;


    //TODO: the below values should come from redis cache
    private static final double DISTANCE_RATE = 1.5; // Rate per kilometer
    private static final double DELIVERY_BASE_FEE = 22;
    private static final double TIME_RATE = 2; // Rate per minute
    //Minimum item total price to make delivery fee zero
    private static final double MIN_ITEM_TOTAL_PRICE = 1000.0;
    private static final double PLATFORM_FEE = 5.0;
    private static final double STATE_GST = 2.5;
    private static final double CENTRAL_GST = 2.5;
    private static final double DELIVERY_TIME_THRESHOLD = 45;
    private static final double DELIVERY_FEE_THRESHOLD = 100;
    private static final double APP_DELIVERY_FEE_DISCOUNT = 40;
    private static final double RESTAURANT_DISCOUNT = 100;
    private static final boolean onConditionDeliveryFeeZero = true;
    private final GoogleMapsClient googleMapsClient;

    public Mono<CartResponse> addItemToCart(CartRequest cartRequest) {
        if (CollectionUtils.isEmpty(cartRequest.getItems())) {
            return Mono.empty();
        }
        Cart cart = cartMapper.convertToCart(cartRequest);
        cart.setUpdatedAt(LocalDateTime.now());
        return calculateOrderPrice(cart.getItems(), cartRequest).flatMap(price -> {
            cart.setPrice(price);
            return cartRepository.addItemToCart(cart).flatMap(cartMapper::convertToCartResponse);
        });

    }

    public Mono<String> clearCart(String userId) {

        return cartRepository.clearCart(userId).thenReturn("Cart cleared successfully");
    }

    private Price buildAllPriceDetails(double totalItemPrice, Taxes taxes, DeliveryFee deliveryFee) {
        Price price = new Price();
        price.setItemsPrice(totalItemPrice);
        price.setTotalTax(taxes);
        price.setPlatformFee(PLATFORM_FEE);
        price.setDeliveryFee(deliveryFee);
        double totalOrderPrice = calculateTotalOrderPrice(totalItemPrice, taxes, deliveryFee);
        price.setTotalPrice(totalOrderPrice);
        return price;
    }

    private static double calculateTotalOrderPrice(double totalItemPrice, Taxes taxes, DeliveryFee deliveryFee) {
        return totalItemPrice + taxes.getCentralGst() + taxes.getStateGst() + deliveryFee.getTotalDeliveryFee() + deliveryFee.getTip();
    }

    /**
     * Calculate the delivery fee based on the total item price, estimated delivery distance, and estimated delivery time.
     *
     * @param totalItemPrice The total price of the items being delivered.
     * @return The calculated delivery fee.
     */
    private DeliveryFee calculateDeliveryFee(double totalItemPrice, double deliveryTip, TravelInfo travelInfo) {
        double distanceFee = (travelInfo.getDistanceInKilometers() * DISTANCE_RATE);
        // Calculate the distance-based fee
        double deliveryFee = DELIVERY_BASE_FEE + distanceFee;
        if (deliveryFee > DELIVERY_FEE_THRESHOLD) {
            deliveryFee = DELIVERY_FEE_THRESHOLD;
        }
        double extraTimeFee = 0;
        if (travelInfo.getTimeInMinutes() > DELIVERY_TIME_THRESHOLD) {

            double extraTime = travelInfo.getTimeInMinutes() - DELIVERY_TIME_THRESHOLD;
            extraTimeFee = extraTime * TIME_RATE;
            deliveryFee = deliveryFee + extraTimeFee;
        }

        // Optionally, you can apply additional logic such as a minimum fee, maximum fee, or discounts
        // For example, if the total item price is above a certain threshold, you could offer free delivery
        if (onConditionDeliveryFeeZero && totalItemPrice > MIN_ITEM_TOTAL_PRICE) {
            deliveryFee = 0;
        }
        double totalDeliveryFee = deliveryFee + extraTimeFee;
        return DeliveryFee.builder().totalDeliveryFee(totalDeliveryFee).deliveryFeeDiscount(APP_DELIVERY_FEE_DISCOUNT).basePrice(DELIVERY_BASE_FEE).distanceFee(distanceFee).timeFee(extraTimeFee).tip(deliveryTip).build();
    }

    private static double calculateTotalItemPrice(List<Item> items) {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    private Mono<Price> calculateOrderPrice(List<Item> items, CartRequest cartRequest) {
        Mono<TravelInfo> travelInfoMono = googleMapsClient.getTravelTimeInfo(cartRequest.getRestaurantLocation().getLatitude(), cartRequest.getRestaurantLocation().getLongitude(), cartRequest.getCustomerLocation().getLatitude(), cartRequest.getCustomerLocation().getLongitude());

        double totalItemPrice = calculateTotalItemPrice(items);
        double sgst = calculateTax(totalItemPrice, STATE_GST);
        double cgst = calculateTax(totalItemPrice, CENTRAL_GST);
        Taxes taxes = prepareTaxes(sgst, cgst);

        return travelInfoMono.map(travelInfo -> {
            DeliveryFee deliveryFee = calculateDeliveryFee(totalItemPrice, cartRequest.getDeliveryTip(), travelInfo);
            return buildAllPriceDetails(totalItemPrice, taxes, deliveryFee);
        });
    }

    private Taxes prepareTaxes(double sgst, double cgst) {
        return Taxes.builder().stateGst(sgst).centralGst(cgst).build();
    }

    public double calculateTax(double price, double percentage) {
        return price * (percentage / 100);
    }

}
