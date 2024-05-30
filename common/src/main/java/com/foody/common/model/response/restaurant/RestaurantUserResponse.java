package com.foody.common.model.response.restaurant;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUserResponse {
    private int id;
    private String name;
    private String email;
}