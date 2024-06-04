package com.foody.common.model.response.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantItemSearchResponse {
   private List<RestaurantResponse> restaurants;
   private List<ItemResponse> items;
}
