package com.foody.common.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {

    private String restaurantId;
    private String userId;
    private String reviewText;
    private double rating;
    private Instant reviewDate;
}
