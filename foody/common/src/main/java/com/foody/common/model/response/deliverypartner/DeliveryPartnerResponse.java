package com.foody.common.model.response.deliverypartner;
import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

import java.time.Instant;

@Data
@RequiredArgsConstructor
public class DeliveryPartnerResponse {
    private UserResponse userResponse;
    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;
}
