package com.foody.data.entity.maps;

import com.foody.common.model.misc.maps.TravelInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    private double latitude;
    private double longitude;
    private String type;
    private String address;
}