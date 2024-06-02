package com.foody.user.service;

import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.data.entity.customer.CustomerUser;
import com.foody.data.repository.customer.CustomerRepository;
import com.foody.data.repository.customer.CustomerUserRepository;
import com.foody.user.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerUserService {

    private final CustomerUserRepository customerUserRepository;

    private final CustomerMapper customerMapper;

    public Mono<CustomerResponse> registerCustomer(CustomerRequest customerRequest) {

        return customerUserRepository.save(customerMapper.convertToCustomerUser(customerRequest))
                .map(customerMapper::convertToCustomerResponse);

    }

    public Mono<CustomerResponse> getCustomerById(String id) {
        return customerUserRepository.findById(id).map(customerMapper::convertToCustomerResponse);
    }

    public Mono<CustomerResponse> updateCustomer(CustomerRequest customerRequest) {
      return customerUserRepository.update(customerMapper.convertToCustomerUser(customerRequest))
              .map(customerMapper::convertToCustomerResponse);
    }

    public Mono<Void> deleteCustomer(String id) {
        return customerUserRepository.deleteById(id);
    }
}

