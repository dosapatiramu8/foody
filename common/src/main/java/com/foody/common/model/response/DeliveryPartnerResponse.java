package com.foody.common.model.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

}