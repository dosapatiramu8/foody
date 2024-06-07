package com.foody.common.model.misc.maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelInfo {
    private double timeInMinutes;
    private double distanceInKilometers;
}