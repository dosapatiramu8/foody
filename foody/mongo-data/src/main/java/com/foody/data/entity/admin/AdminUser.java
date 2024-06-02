package com.foody.data.entity.admin;

import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {
    private Long adminUserId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private List<Address> addresses;
    private Instant createdAt;
    private Instant updatedAt;
}
