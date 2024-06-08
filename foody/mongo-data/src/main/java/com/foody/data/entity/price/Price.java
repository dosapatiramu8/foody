package com.foody.data.entity.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Price {

    private double itemsPrice;
    private double platformFee;
    private DeliveryFee deliveryFee;
    private Taxes totalTax;
    private double packingCharge;
    private double totalPrice;
    private DiscountShare discountShare;
}
