package com.foody.common.model.response.order;

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
    private double deliveryCharge;
    private double surcharge;
    private double ourFee;
    private double gstTax;
    private double totalTax;
    private double totalPrice;
}
