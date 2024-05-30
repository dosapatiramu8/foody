package com.foody.delivery.service;

import com.foody.common.model.request.DeliveryPartnerRequest;
import com.foody.common.model.response.DeliveryPartnerResponse;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeliveryPartnerService {
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public Mono<DeliveryPartnerResponse> registerDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        DeliveryPartner deliveryPartner = new DeliveryPartner();
        deliveryPartner.setName(deliveryPartnerRequest.getName());
        deliveryPartner.setEmail(deliveryPartnerRequest.getEmail());
        deliveryPartner.setPhoneNumber(deliveryPartnerRequest.getPhoneNumber());

        return deliveryPartnerRepository.save(deliveryPartner)
            .map(dp -> new DeliveryPartnerResponse(
                dp.getId(),
                dp.getName(),
                dp.getEmail(),
                dp.getPhoneNumber()
            ));
    }

    public Mono<DeliveryPartnerResponse> getDeliveryPartnerById(Long id) {
        return deliveryPartnerRepository.findById(id)
            .map(dp -> new DeliveryPartnerResponse(
                dp.getId(),
                dp.getName(),
                dp.getEmail(),
                dp.getPhoneNumber()
            ));
    }

    public Mono<DeliveryPartnerResponse> updateDeliveryPartner(Long id, DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerRepository.findById(id)
            .flatMap(existingPartner -> {
                existingPartner.setName(deliveryPartnerRequest.getName());
                existingPartner.setEmail(deliveryPartnerRequest.getEmail());
                existingPartner.setPhoneNumber(deliveryPartnerRequest.getPhoneNumber());
                return deliveryPartnerRepository.save(existingPartner);
            })
            .map(dp -> new DeliveryPartnerResponse(
                dp.getId(),
                dp.getName(),
                dp.getEmail(),
                dp.getPhoneNumber()
            ));
    }

    public Mono<Void> deleteDeliveryPartner(Long id) {
        return deliveryPartnerRepository.deleteById(id);
    }
}
