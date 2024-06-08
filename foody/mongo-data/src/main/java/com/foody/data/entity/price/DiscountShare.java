package com.foody.data.entity.price;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountShare {
    private double appDiscount;
    private double restaurantDiscount;
}
