package com.foody.common.model.request.deliverypartner;

import com.foody.common.model.maps.LocationDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryPartnerLocation {

    private String deliveryPartnerId;
    private String deviceAccessToken;
    private LocationDetails locationDetails;
}
