package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TokenDTO {

    @NotNull(message = "Field 'token' cannot be null.")
    @NotBlank(message = "Field 'token' cannot be empty.")
    @Pattern(regexp = "(^[\\w-]*\\.[\\w-]*\\.[\\w-]*$)", message = "Provided string is not a JWT token.")
    private String token;
}
