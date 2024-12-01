package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewHospitalDTO {

    @NotBlank(message = "Field 'name' cannot be empty.")
    @NotNull(message = "Field 'name' cannot be null.")
    private String name;

    @NotBlank(message = "Field 'phoneNumber' cannot be empty.")
    @NotNull(message = "Field 'phoneNumber' cannot be null.")
    private String phoneNumber;

    @NotBlank(message = "Field 'street' cannot be empty.")
    @NotNull(message = "Field 'street' cannot be null.")
    private String street;

    @NotNull(message = "Field 'buildingNo' cannot be empty.")
    private String buildingNo;

    @NotNull(message = "Field 'zipCode' cannot be null.")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "Field 'zipCode' must match the pattern: XX-XXX")
    private String zipCode;

    @NotBlank(message = "Field 'city' cannot be empty.")
    @NotNull(message = "Field 'city' cannot be null.")
    private String city;

}
