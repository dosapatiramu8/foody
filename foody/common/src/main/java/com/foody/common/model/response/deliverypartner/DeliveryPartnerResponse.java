package com.foody.common.model.response.deliverypartner;
import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeliveryPartnerResponse extends UserResponse {
    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;
}
