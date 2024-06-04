package com.foody.common.mapper;

import com.foody.common.model.request.order.OrderRequest;
import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.response.order.Price;
import com.foody.data.entity.order.Order;
import com.foody.data.misc.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final MenuMapper menuMapper;

    public Order convertToOrder(OrderRequest orderRequest){
        Order order = new Order();
        BeanUtils.copyProperties(orderRequest, order);
        List<Item> itemList = orderRequest.getItems().stream()
                .map(menuMapper::convertToItem)
                .toList();
        order.setItems(itemList);
        return order;
    }

    public OrderResponse convertToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order,orderResponse);
        Price price = new Price();
        BeanUtils.copyProperties(order.getPrice(),price);
        orderResponse.setPrice(price);
        List<ItemResponse> itemResponseList = order.getItems().stream()
                .map(menuMapper::convertToItemResponse)
                .toList();
        orderResponse.setItems(itemResponseList);
        return orderResponse;
    }
}
