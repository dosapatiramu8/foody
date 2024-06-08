package com.foody.common.mapper;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.price.Price;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.entity.customer.CustomerOrder;
import com.foody.data.entity.order.Order;
import com.foody.data.entity.order.Payment;
import com.foody.data.entity.restaurant.RestaurantOrder;
import com.foody.data.misc.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ItemMapper itemMapper;

    public OrderResponse convertToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order,orderResponse);
        Price price = new Price();
        BeanUtils.copyProperties(order.getPrice(),price);
        orderResponse.setPrice(price);
        List<ItemResponse> itemResponseList = order.getItems().stream()
                .map(itemMapper::convertToItemResponse)
                .toList();
        orderResponse.setItems(itemResponseList);
        return orderResponse;
    }



    public Order convertToOrder(OrderRequest orderRequest) {
        RestaurantRequest restaurantRequest = orderRequest.getRestaurantRequest();
        RestaurantOrder restaurantOrder = convertToRestaurantOrder(orderRequest, restaurantRequest);
        CustomerOrder customerOrder = convertToCustomerOrder(orderRequest);
        Payment payment = new Payment();
        BeanUtils.copyProperties(orderRequest.getPaymentDetails(), payment);

        return Order.builder()
                .orderId(orderRequest.getOrderId())
                .restaurant(restaurantOrder)
                .customer(customerOrder).payment(payment)
                .price(orderRequest.getPrice())
                .items(orderRequest.getItems())
                .deliveryTimeInMinutes(orderRequest.getDeliveryTimeInMinutes())
                .deliveryDistanceInKilometers(orderRequest.getDeliveryDistanceInKilometers())
                .build();

    }

    private CustomerOrder convertToCustomerOrder(OrderRequest orderRequest) {
        CustomerOrder customerOrder = new CustomerOrder();
        BeanUtils.copyProperties(orderRequest.getCustomerRequest(), customerOrder);
        Address address = new Address();
        BeanUtils.copyProperties(orderRequest.getCustomerRequest().getPrimaryCurrentAddress(), address);
        customerOrder.setAddress(address);
        return customerOrder;
    }

    private RestaurantOrder convertToRestaurantOrder(OrderRequest orderRequest, RestaurantRequest restaurantRequest) {
        RestaurantOrder restaurantOrder = new RestaurantOrder();
        BeanUtils.copyProperties(orderRequest.getRestaurantRequest(), restaurantOrder);
        Address address = new Address();
        BeanUtils.copyProperties(restaurantRequest.getAddressRequest(), address);
        restaurantOrder.setAddress(address);
        return restaurantOrder;
    }

}
