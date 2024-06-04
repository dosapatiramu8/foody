package com.foody.user.service;

import com.foody.common.mapper.CustomerMapper;
import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.data.repository.customer.CustomerUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerService {

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

