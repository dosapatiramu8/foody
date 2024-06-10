package com.foody.common.model.response.customer;

import com.foody.common.model.response.misc.AddressResponse;
import com.foody.common.model.response.misc.UserResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerResponse {
    private UserResponse userResponse;
    private String customerId;
    private AddressResponse primaryCurrentAddress;
    private List<AddressResponse> addresses;
}
