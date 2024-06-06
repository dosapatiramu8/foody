package com.foody.common.model.response.misc;

import com.foody.common.model.misc.Location;
import com.foody.common.model.request.misc.AddressRequest;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private List<String> addresses;
    private Instant createdAt;
    private Instant updatedAt;
    private Location currentLocation;
}
