package com.foody.common.model.request.misc;

import com.foody.common.model.misc.Location;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<AddressRequest> addressRequestList;
    private Location currentLocation;
    private Instant createdAt;
    private Instant updatedAt;
}
