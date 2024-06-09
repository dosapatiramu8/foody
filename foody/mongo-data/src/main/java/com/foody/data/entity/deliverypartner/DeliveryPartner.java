package com.foody.data.entity.deliverypartner;

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
    private Long id;
    private String deliveryPartnerId;
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;
}
