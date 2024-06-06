package com.foody.common.model.request.misc;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AddressRequest {
    private String addressType; //Home Office Friends Family
    private String houseNum;
    private String apartmentRoadArea;
    private String landMark;
    private String state;
    private String city;
    private String pinCode;
    private String saveAs;
    private boolean isPrimary;
    private double latitude;
    private double longitude;

}
