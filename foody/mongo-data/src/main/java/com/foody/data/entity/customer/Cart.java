package com.foody.data.entity.customer;

import com.foody.data.entity.price.Price;
import com.foody.data.misc.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    private String id;
    private String userId;
    private String restaurantId;
    private String restaurantName;
    private List<Item> items;
    private Price price;
    private double deliveryTip;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
