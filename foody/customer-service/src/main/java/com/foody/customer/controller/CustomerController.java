package com.foody.customer.controller;

import com.foody.common.model.request.customer.CustomerRequest;
import com.foody.common.model.response.customer.CustomerResponse;
import com.foody.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public Mono<ResponseEntity<CustomerResponse>> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.registerCustomer(customerRequest).map(customerResponse -> new ResponseEntity<>(customerResponse, HttpStatus.CREATED));
    }

    @GetMapping("/allCustomers")
    public Mono<ResponseEntity<List<CustomerResponse>>> findAllCustomers(@RequestParam int page, @RequestParam int size,
                                                                         @RequestParam String sortByField, @RequestBody CustomerRequest customerRequest ) {
        return customerService.findAllCustomers(page, size, sortByField, customerRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerResponse>> getCustomer(@PathVariable String id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.updateCustomer(customerRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

