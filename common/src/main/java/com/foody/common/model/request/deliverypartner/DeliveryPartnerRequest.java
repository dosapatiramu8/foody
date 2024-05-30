package com.foody.common.model.request.deliverypartner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequest {
    private String name;
    private String email;
    private String phoneNumber;


}
