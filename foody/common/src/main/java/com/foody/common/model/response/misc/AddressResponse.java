package com.foody.common.model.response.misc;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AddressResponse {
    private String houseNum;
    private String apartmentRoadArea;
    private String landMark;
    private String state;
    private String city;
    private String pinCode;
    private String saveAs;
    private boolean isPrimary;
}
