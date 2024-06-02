package com.foody.common.model.request.deliverypartner;

import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DeliveryPartnerRequest extends UserRequest {

    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;

}
