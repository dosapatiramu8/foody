package com.foody.common.model.details;

import com.foody.common.model.misc.maps.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPartnerDetails {
    private String deliveryPartnerId;
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private Location deliveryPartnerLocation;
}
