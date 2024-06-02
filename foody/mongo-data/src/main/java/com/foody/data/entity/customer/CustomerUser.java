package com.foody.data.entity.customer;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUser {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String referralCode;
    private List<Address> addresses;
    private Instant createdAt;
    private Instant updatedAt;
}
