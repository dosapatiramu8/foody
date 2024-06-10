package com.foody.data.entity.deliverypartner;

import com.foody.data.entity.user.User;
import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartner {

    private User user;
    private String deliveryPartnerId;
    private Address address;
    private Instant createdAt;
    private Instant updatedAt;
    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;
}
