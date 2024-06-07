package com.foody.delivery.controller;

import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerResponse;
import com.foody.common.model.response.restaurant.RestaurantItemSearchResponse;
import com.foody.delivery.service.DeliveryPartnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/delivery-partner")
@AllArgsConstructor
public class DeliveryPartnerController {
    private final DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DeliveryPartnerResponse> registerDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerService.registerDeliveryPartner(deliveryPartnerRequest);
    }

    @GetMapping("/{id}")
    public Mono<DeliveryPartnerResponse> getDeliveryPartner(@PathVariable String id) {
        return deliveryPartnerService.getDeliveryPartnerById(id);
    }

    @PutMapping("/{id}")
    public Mono<DeliveryPartnerResponse> updateDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerService.updateDeliveryPartner(deliveryPartnerRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteDeliveryPartner(@PathVariable String id) {
        return deliveryPartnerService.deleteDeliveryPartner(id);
    }

    @GetMapping("/assignDeliveryPartner")
    public Mono<String> assignDeliveryPartner(@RequestParam double latitude, @RequestParam double longitude){
        return deliveryPartnerService.assignDeliveryPartner(latitude,longitude);
    }
}


