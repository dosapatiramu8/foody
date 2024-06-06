package com.foody.common.mapper;

import com.foody.common.model.response.order.OrderResponse;
import com.foody.common.model.response.order.Price;
import com.foody.common.model.response.restaurant.ItemResponse;
import com.foody.data.entity.order.Order;
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


}
