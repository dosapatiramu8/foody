package com.foody.common.mapper;

import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.data.entity.customer.CustomerUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerUser convertToCustomerUser(CustomerRequest customerRequest){
        CustomerUser customerUser = new CustomerUser();
        BeanUtils.copyProperties(customerRequest,customerUser);
        return customerUser;
    }

    public CustomerResponse convertToCustomerResponse(CustomerUser customerUser){
        CustomerResponse customerResponse = new CustomerResponse();
        BeanUtils.copyProperties(customerUser,customerUser);
        return customerResponse;
    }
}
