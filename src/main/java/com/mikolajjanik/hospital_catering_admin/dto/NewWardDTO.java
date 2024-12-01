package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Dietician;
import com.mikolajjanik.hospital_catering_admin.entity.Hospital;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class NewWardDTO {

    @NotBlank(message = "Field 'name' cannot be empty.")
    @NotNull(message = "Field 'name' cannot be null.")
    private String name;

    @NotBlank(message = "Field 'phoneNumber' cannot be empty.")
    @NotNull(message = "Field 'phoneNumber' cannot be null.")
    @Pattern(regexp = "^(\\d{2}\\s?\\d{3}\\s?\\d{2}\\s?\\d{2}|\\d{2}\\s?\\d{4}\\s?\\d{3}|\\d{3}\\s?\\d{3}\\s?\\d{3})$",
            message = "Invalid phone number format")
    private String phoneNumber;

    private Hospital hospital;

    private List<Dietician> dieticians;
}
