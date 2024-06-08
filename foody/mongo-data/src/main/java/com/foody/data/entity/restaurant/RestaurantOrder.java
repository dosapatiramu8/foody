package com.foody.data.entity.restaurant;

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
public class RestaurantOrder {
    private String restaurantId;
    private String restaurantName;
    private String phoneNumber;
    private String emailId;
    private String restaurantDescription;
    private Address address;
    @GeoSpatialIndexed
    @Field("location")
    private GeoJsonPoint location;

}