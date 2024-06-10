package com.foody.customer.service;

import com.foody.common.mapper.CustomerMapper;
import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.data.repository.customer.CustomerUserRepository;
import com.foody.data.repository.util.LogMessageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerUserRepository customerUserRepository;

    private final CustomerMapper customerMapper;


    public Mono<CustomerResponse> registerCustomer(CustomerRequest customerRequest) {
        return LogMessageUtils.exec("/register customer ", customerRequest,
                () -> customerUserRepository.save(customerMapper.convertToCustomerUser(customerRequest))
                        .map(customerMapper::convertToCustomerResponse));
    }


    public Mono<CustomerResponse> getCustomerById(String id) {
        return LogMessageUtils.exec("/find customer by ID ", id,
                () -> customerUserRepository.findById(id).map(customerMapper::convertToCustomerResponse));
    }


    public Mono<CustomerResponse> updateCustomer(CustomerRequest customerRequest) {
        return LogMessageUtils.exec("/update customer", customerRequest,
                () -> customerUserRepository.save(customerMapper.convertToCustomerUser(customerRequest))
                        .map(customerMapper::convertToCustomerResponse));
    }


    public Mono<Void> deleteCustomer(String id) {
        return LogMessageUtils.exec("delete customer by ID ", id,
                () -> customerUserRepository.deleteById(id));
    }
    public Mono<List<CustomerResponse>> findAllCustomers(int page, int size, String sortByField, CustomerRequest customerRequest) {
        return LogMessageUtils.exec("/find all customers ", customerRequest,
                () -> customerUserRepository.findAll(page, size, sortByField, customerMapper.convertToCustomerUser(customerRequest))
                        .map(customerMapper::convertToCustomerResponse)
                        .collectList());
    }

}

