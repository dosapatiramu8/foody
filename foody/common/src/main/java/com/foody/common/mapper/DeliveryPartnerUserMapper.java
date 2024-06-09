package com.foody.common.mapper;

import com.foody.common.model.request.deliverypartner.DeliveryPartnerRequest;
import com.foody.common.model.response.deliverypartner.DeliveryPartnerResponse;
import com.foody.data.entity.deliverypartner.DeliveryPartner;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DeliveryPartnerUserMapper {
    
    public DeliveryPartner convertToDeliveryPartnerUser(DeliveryPartnerRequest deliveryPartnerRequest){
        DeliveryPartner deliveryPartnerUser = new DeliveryPartner();
        BeanUtils.copyProperties(deliveryPartnerRequest, deliveryPartnerUser);
        return deliveryPartnerUser;
    }

    public DeliveryPartnerResponse convertToDeliveryPartnerResponse(DeliveryPartner deliveryPartner){
        DeliveryPartnerResponse deliveryPartnerResponse = new DeliveryPartnerResponse();
        BeanUtils.copyProperties(deliveryPartner, deliveryPartnerResponse);
        return deliveryPartnerResponse;
    }
}
