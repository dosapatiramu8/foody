package com.foody.data.entity.customer;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrder {

    private String customerId;
    private String customerName;
    private String phoneNumber;
    private String emailId;
    private Address address;
    @GeoSpatialIndexed
    @Field("location")
    private GeoJsonPoint location;
}
