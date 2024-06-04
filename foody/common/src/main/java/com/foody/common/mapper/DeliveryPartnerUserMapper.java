package com.foody.common.mapper;

import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerResponse;
import com.foody.data.entity.deliverypartner.DeliveryPartnerUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPartnerUserMapper {
    
    public DeliveryPartnerUser convertToDeliveryPartnerUser(DeliveryPartnerRequest deliveryPartnerRequest){
        DeliveryPartnerUser deliveryPartnerUser = new DeliveryPartnerUser();
        BeanUtils.copyProperties(deliveryPartnerRequest, deliveryPartnerUser);
        return deliveryPartnerUser;
    }

    public DeliveryPartnerResponse convertToDeliveryPartnerResponse(DeliveryPartnerUser deliveryPartnerUser){
        DeliveryPartnerResponse deliveryPartnerResponse = new DeliveryPartnerResponse();
        BeanUtils.copyProperties(deliveryPartnerUser, deliveryPartnerResponse);
        return deliveryPartnerResponse;
    }
}
