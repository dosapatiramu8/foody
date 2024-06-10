package com.foody.common.model.request.misc;

import com.foody.common.model.maps.LocationDetails;
import lombok.*;

@Data
@RequiredArgsConstructor
public class AddressRequest {
    private String address;
    private String saveAs;
    private String flatNumber;
    private String city;
    private String areaName;
    private String pinCode;
    private boolean isPrimary;
    private LocationDetails location;

}
