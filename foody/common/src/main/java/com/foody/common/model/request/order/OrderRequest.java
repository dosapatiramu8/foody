package com.foody.common.model.request.order;

import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.request.payment.PaymentRequest;
import com.foody.common.model.request.restaurant.RestaurantRequest;
import com.foody.common.model.response.order.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String orderId;
    private CustomerRequest customerRequest;
    private RestaurantRequest restaurantRequest;
    private Price price;
    private PaymentRequest paymentOptions;

}
