package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderItemDTO {
    private Meal meal;
    private LocalDate date;

    public OrderItemDTO(Meal meal, LocalDate date) {
        this.meal = meal;
        this.date = date;
    }
}
