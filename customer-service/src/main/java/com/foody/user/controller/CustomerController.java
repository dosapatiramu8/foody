package com.foody.user.controller;

import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.user.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerResponse> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.registerCustomer(customerRequest)
                .map(c -> {
                    CustomerResponse response = new CustomerResponse();
                    response.setId(c.getId());
                    response.setName(c.getName());
                    response.setEmail(c.getEmail());
                    response.setPhoneNumber(c.getPhoneNumber());
                    return response;
                });
    }

    @GetMapping("/{id}")
    public Mono<CustomerResponse> getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(c -> {
                    CustomerResponse response = new CustomerResponse();
                    response.setId(c.getId());
                    response.setName(c.getName());
                    response.setEmail(c.getEmail());
                    response.setPhoneNumber(c.getPhoneNumber());
                    return response;
                });
    }

    @PutMapping("/{id}")
    public Mono<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(id, customerRequest)
                .map(c -> {
                    CustomerResponse response = new CustomerResponse();
                    response.setId(c.getId());
                    response.setName(c.getName());
                    response.setEmail(c.getEmail());
                    response.setPhoneNumber(c.getPhoneNumber());
                    return response;
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteCustomer(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }
}

