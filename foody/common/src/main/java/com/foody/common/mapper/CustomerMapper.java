package com.foody.common.mapper;

import com.foody.common.model.maps.LocationDetails;
import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.request.misc.AddressRequest;
import com.foody.common.model.request.misc.UserRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.common.model.response.misc.AddressResponse;
import com.foody.common.model.response.misc.UserResponse;
import com.foody.data.entity.customer.CustomerUser;
import com.foody.data.entity.maps.Location;
import com.foody.data.entity.user.User;
import com.foody.data.misc.Address;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class CustomerMapper {

    public CustomerUser convertToCustomerUser(CustomerRequest customerRequest){
        CustomerUser customerUser = new CustomerUser();
        BeanUtils.copyProperties(customerRequest,customerUser);
        User user = new User();
        BeanUtils.copyProperties(customerRequest.getUserRequest(), user);
        customerUser.setUser(user);
       if(!CollectionUtils.isEmpty(customerRequest.getAddressRequestList())){
           List<Address> addressList = customerRequest.getAddressRequestList().stream().
                   map(this::convertAddressRequestToAddress).toList();
           customerUser.setAddresses(addressList);

       }
        customerUser.setAddress(convertAddressRequestToAddress(customerRequest.getPrimaryCurrentAddress()));
        customerUser.setCreatedAt(LocalDateTime.now());
        customerUser.setUpdatedAt(LocalDateTime.now());
        return customerUser;
    }

    public Address convertAddressRequestToAddress(AddressRequest addressRequest){
        Address address = new Address();
        if(Objects.nonNull(addressRequest)){

            BeanUtils.copyProperties(addressRequest, address);
            Location location = new Location();
            BeanUtils.copyProperties(addressRequest.getLocation(), location);
            address.setLocation(location);

        }
        return address;
    }


    public AddressResponse convertAddressToAddressResponse(Address address){
        AddressResponse addressResponse = new AddressResponse();
        if(Objects.nonNull(address)){
            BeanUtils.copyProperties(address, addressResponse);
            LocationDetails locationDetails = new LocationDetails();
            if(Objects.nonNull(address.getLocation())){
                BeanUtils.copyProperties(address.getLocation(), locationDetails);
                addressResponse.setLocationDetails(locationDetails);
            }

        }
        return addressResponse;
    }

    public CustomerResponse convertToCustomerResponse(CustomerUser customerUser){
        CustomerResponse customerResponse = CustomerResponse.builder().build();
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(customerUser.getUser(), userResponse);
        customerResponse.setUserResponse(userResponse);
        BeanUtils.copyProperties(customerUser,customerResponse);
        if(!CollectionUtils.isEmpty(customerUser.getAddresses())){
            List<AddressResponse> addressList = customerUser.getAddresses().stream().
                    map(this::convertAddressToAddressResponse).toList();
            customerResponse.setAddresses(addressList);
            customerResponse.setPrimaryCurrentAddress(convertAddressToAddressResponse(customerUser.getAddress()));
        }
        return customerResponse;
    }
}
