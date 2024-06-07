package com.foody.delivery.service;

import com.foody.common.mapper.DeliveryPartnerUserMapper;
import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerResponse;
import com.foody.data.repository.delivery.DeliveryPartnerUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeliveryPartnerService {

    private final DeliveryPartnerUserRepository deliveryPartnerUserRepository;

    private final DeliveryPartnerUserMapper deliveryPartnerUserMapper;

    public Mono<DeliveryPartnerResponse> registerDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerUserRepository.save(deliveryPartnerUserMapper.convertToDeliveryPartnerUser(deliveryPartnerRequest))
                .map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<DeliveryPartnerResponse> getDeliveryPartnerById(String id) {
        return deliveryPartnerUserRepository.findById(id).map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<DeliveryPartnerResponse> updateDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerUserRepository.update(deliveryPartnerUserMapper.convertToDeliveryPartnerUser(deliveryPartnerRequest))
                .map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<Void> deleteDeliveryPartner(String id) {
        return deliveryPartnerUserRepository.deleteById(id);
    }

    public Mono<String> assignDeliveryPartner(double latitude, double longitude) {
        deliveryPartnerUserRepository.findNearByDeliveryPartners(latitude, longitude);
    }
}

