package com.mikolajjanik.hospital_catering_admin.dto;

import lombok.Data;

@Data
public class PaymentInfoDTO {
    private int amount;
    private String currency;
}
