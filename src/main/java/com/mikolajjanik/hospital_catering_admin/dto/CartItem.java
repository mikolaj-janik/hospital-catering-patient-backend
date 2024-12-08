package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Meal;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CartItem {
    private LocalDate date;
    private Meal meal;
}
