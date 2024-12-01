package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NewPatientDTO {
    @NotNull(message = "Field 'name' cannot be null.")
    @NotBlank(message = "Field 'name' cannot be empty.")
    private String name;

    @NotNull(message = "Field 'surname' cannot be null.")
    @NotBlank(message = "Field 'surname' cannot be empty.")
    private String surname;

    @NotNull(message = "Field 'pesel' cannot be null.")
    @NotBlank(message = "Field 'pesel' cannot be empty.")
    @Pattern(regexp = "[0-9]{4}[0-3]{1}[0-9]{1}[0-9]{5}",
            message = "This is not a pesel string.")
    private String pesel;

    @NotNull(message = "Field 'defaultPassword' cannot be null.")
    @NotBlank(message = "Field 'defaultPassword' cannot be empty.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$",
            message = "Password must contain one digit from 1 to 9, " +
                    "one lowercase letter, one uppercase letter, one special character, " +
                    "no space, and it must be 8-16 characters long")
    private String defaultPassword;

    @Email(message = "Field 'email': Provided string is not an email.")
    private String email;

    @NotNull(message = "Field 'wardId' cannot be null.")
    @Min(value = 1, message = "Field 'wardId' must be greater than 0")
    private Long wardId;

    @NotNull(message = "Field 'dietId' cannot be null.")
    @Min(value = 1, message = "Field 'dietId' must be greater than 0")
    private Long dietId;
}
