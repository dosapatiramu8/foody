package com.foody.common.model.request.customer;

import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private UserRequest userRequest;
    private String customerId;
    private List<AddressRequest> addressRequestList;
    private AddressRequest primaryCurrentAddress;
    private String referralCode;

}
