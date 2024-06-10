package com.foody.data.entity.customer;

import com.foody.data.entity.user.User;
import com.foody.data.misc.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("customer")
public class CustomerUser {

    private User user;
    private String customerId;
    private String referralCode;
    private List<Address> addresses;
    private Address address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
