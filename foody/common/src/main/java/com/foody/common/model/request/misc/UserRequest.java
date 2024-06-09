package com.foody.common.model.request.misc;

import com.foody.common.model.maps.LocationDetails;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<AddressRequest> addressRequestList;
    private LocationDetails currentLocation;
    private Instant createdAt;
    private Instant updatedAt;

}
