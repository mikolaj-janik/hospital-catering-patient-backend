package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginUserDTO {

    @NotNull(message = "Field 'login' cannot be null.")
    @NotBlank(message = "Field 'login' cannot be empty.")
    @Pattern(regexp = "[0-9]{4}[0-3]{1}[0-9]{1}[0-9]{5}",
            message = "This is not a pesel string.")
    private String login;

    @NotNull(message = "Field 'password' cannot be null.")
    @NotBlank(message = "Field 'password' cannot be empty.")
    private String password;

}
