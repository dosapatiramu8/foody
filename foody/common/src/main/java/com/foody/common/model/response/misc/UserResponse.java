package com.foody.common.model.response.misc;

import com.foody.common.model.maps.LocationDetails;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private List<AddressResponse> addresses;
    private Instant createdAt;
    private Instant updatedAt;
}
