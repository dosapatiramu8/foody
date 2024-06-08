package com.foody.common.model.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAvailability {
    private LocalTime from;
    private LocalTime to;
    private boolean availability;
}
