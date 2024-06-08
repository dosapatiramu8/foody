package com.foody.common.model.maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDetails {

    private double latitude;
    private double longitude;
    private String type;
    private String address;
    private TravelInfo travelInfo;
}