package com.foody.data.entity.restaurant;

import com.foody.data.misc.Address;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String restaurantName;
    private String restaurantDescription;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private BigDecimal rating;
    @GeoSpatialIndexed
    @Field("location")
    private GeoJsonPoint location;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Item> items;
    private double latitude;
    private double longitude;
    private boolean isOpened;
    private Instant openTime;
    private Instant closeTime;

}
