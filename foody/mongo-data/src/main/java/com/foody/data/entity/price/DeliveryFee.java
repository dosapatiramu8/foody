package com.foody.data.entity.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryFee {
    private double basePrice;
    private double timeFee;
    private double distanceFee;
    private double deliveryFeeDiscount;
    private double tip;
    private double surCharge;
    private double totalDeliveryFee;
}
