package com.foody.common.model.request.customer;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
