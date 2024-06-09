package com.foody.common.mapper;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.entity.order.Order;
import com.foody.data.entity.order.Payment;
import com.foody.data.entity.price.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ItemMapper itemMapper;

    public OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        Price price = new Price();
        BeanUtils.copyProperties(order.getPrice(), price);
        orderResponse.setPrice(price);
        List<ItemResponse> itemResponseList = order.getItems().stream()
                .map(itemMapper::convertToItemResponse)
                .toList();
        orderResponse.setItems(itemResponseList);
        return orderResponse;
    }


    public Order convertToOrder(OrderRequest orderRequest) {

        Payment payment = new Payment();
        BeanUtils.copyProperties(orderRequest.getPaymentDetails(), payment);

        return Order.builder()
                .orderId(orderRequest.getOrderId())
                .restaurant(orderRequest.getRestaurant())
                .customer(orderRequest.getCustomer())
                .deliveryPartnerOrder(orderRequest.getDeliveryPartnerOrder())
                .payment(payment)
                .price(orderRequest.getPrice())
                .items(orderRequest.getItems())
                .deliveryTimeInMinutes(orderRequest.getDeliveryTimeInMinutes())
                .deliveryDistanceInKilometers(orderRequest.getDeliveryDistanceInKilometers())
                .build();

    }

}
