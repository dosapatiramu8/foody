package com.foody.delivery.service;

import com.foody.common.mapper.DeliveryPartnerUserMapper;
import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerResponse;
import com.foody.data.repository.delivery.DeliveryPartnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;

    private final DeliveryPartnerUserMapper deliveryPartnerUserMapper;

    public Mono<DeliveryPartnerResponse> registerDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerRepository.save(deliveryPartnerUserMapper.convertToDeliveryPartnerUser(deliveryPartnerRequest))
                .map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<DeliveryPartnerResponse> getDeliveryPartnerById(String id) {
        return deliveryPartnerRepository.findById(id).map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<DeliveryPartnerResponse> updateDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        return deliveryPartnerRepository.update(deliveryPartnerUserMapper.convertToDeliveryPartnerUser(deliveryPartnerRequest))
                .map(deliveryPartnerUserMapper::convertToDeliveryPartnerResponse);
    }

    public Mono<Void> deleteDeliveryPartner(String id) {
        return deliveryPartnerRepository.deleteById(id);
    }

}

