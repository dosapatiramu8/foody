package com.foody.common.model.request.misc;


import com.foody.common.model.misc.maps.Location;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AddressRequest {
    private String address;
    private String saveAs;
    private String flatNumber;
    private String city;
    private String areaName;
    private String pinCode;
    private boolean isPrimary;
    private Location location;

}
