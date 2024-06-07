package com.foody.data.entity.deliverypartner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerLocations {
    @Id
    private String id;
    private String deliveryPartnerLocationId;
    private String deliveryPartnerId;
    private String latitude;
    private String longitude;
    private String address;

}
