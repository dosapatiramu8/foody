package com.foody.user.service;

import com.foody.common.model.request.CustomerRequest;
import com.foody.common.model.response.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Mono<CustomerResponse> registerCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        return customerRepository.save(customer);
    }

    public Mono<CustomerResponse> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Mono<CustomerResponse> updateCustomer(Long id, CustomerRequest customerRequest) {
        return customerRepository.findById(id)
                .flatMap(existingCustomer -> {
                    existingCustomer.setName(customerRequest.getName());
                    existingCustomer.setEmail(customerRequest.getEmail());
                    existingCustomer.setPhoneNumber(customerRequest.getPhoneNumber());
                    return customerRepository.save(existingCustomer);
                });
    }

    public Mono<Void> deleteCustomer(Long id) {
        return customerRepository.deleteById(id);
    }
}

}
