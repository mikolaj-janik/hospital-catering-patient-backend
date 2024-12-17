package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Patient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class NewOrderDTO {
    @NotNull(message = "Field 'patient' cannot be null.")
    private Patient patient;

    @NotNull(message = "Field 'totalPrice' cannot be null.")
    private double totalPrice;

    @NotNull(message = "Field 'cartItems' cannot be null")
    private List<CartItem> cartItems;
}