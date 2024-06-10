package com.foody.data.entity.restaurant;

import com.foody.data.entity.user.User;
import com.foody.data.misc.Address;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private User user;
    private String restaurantId;
    private String restaurantName;
    private String restaurantDescription;
    private BigDecimal rating;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private List<Item> items;
    private boolean isOpened;
    private Instant openTime;
    private Instant closeTime;
    private Address address;

}
