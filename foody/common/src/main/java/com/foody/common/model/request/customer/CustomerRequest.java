package com.foody.common.model.request.customer;

import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class CustomerRequest extends UserRequest {


    private String referralCode;

}
