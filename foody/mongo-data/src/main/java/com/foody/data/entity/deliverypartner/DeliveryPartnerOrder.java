package com.foody.data.entity.deliverypartner;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerOrder {
    private String deliveryPartnerId;
    private String deviceAccessToken;
    private String deliveryPartnerName;
    private String phoneNumber;
    private String emailId;
}