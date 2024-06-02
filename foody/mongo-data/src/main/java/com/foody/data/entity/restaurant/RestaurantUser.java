package com.foody.data.entity.restaurant;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUser {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<Address> addresses;
    private BigDecimal rating;
    private Instant createdAt;
    private Instant updatedAt;
}
