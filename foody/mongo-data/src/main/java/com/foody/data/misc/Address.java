package com.foody.data.misc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String addressId;
    private String houseNum;
    private String apartmentRoadArea;
    private String landMark;
    private String state;
    private String city;
    private String zipCode;
    private String country;
    private String saveAs;
    private boolean isPrimary;
}
