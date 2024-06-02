package com.foody.data.entity.deliverypartner;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerUser {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Address> addresses;
    private Instant createdAt;
    private Instant updatedAt;
    private String vehicleType;
    private String vehicleNumber;
    private String chassisNumber;
    private Instant registrationDate;
}
