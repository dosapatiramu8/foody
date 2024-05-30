package com.foody.common.model.request.restaurant;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUserRequest {
    private String name;
    private String email;
}