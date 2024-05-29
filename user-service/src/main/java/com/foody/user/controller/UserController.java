package com.foody.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> registerCustomer(@RequestBody Customer customer) {
        return userService.registerCustomer(customer);
    }

    @PostMapping("/register/delivery-partner")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DeliveryPartner> registerDeliveryPartner(@RequestBody DeliveryPartner deliveryPartner) {
        return userService.registerDeliveryPartner(deliveryPartner);
    }

    @PostMapping("/register/restaurant-partner")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RestaurantPartner> registerRestaurantPartner(@RequestBody RestaurantPartner restaurantPartner) {
        return userService.registerRestaurantPartner(restaurantPartner);
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Admin> registerAdmin(@RequestBody Admin admin) {
        return userService.registerAdmin(admin);
    }

    @PostMapping("/register/support")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Support> registerSupport(@RequestBody Support support) {
        return userService.registerSupport(support);
    }
}
