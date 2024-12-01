package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EditPatientDTO {

    @NotNull(message = "Field 'id' cannot be null.")
    @Min(value = 1, message = "Field 'id' must be greater than 0")
    private Long id;

    @NotNull(message = "Field 'name' cannot be null.")
    @NotBlank(message = "Field 'name' cannot be empty.")
    private String name;

    @NotNull(message = "Field 'surname' cannot be null.")
    @NotBlank(message = "Field 'surname' cannot be empty.")
    private String surname;

    @NotNull(message = "Field 'login' cannot be null.")
    @NotBlank(message = "Field 'login' cannot be empty.")
    @Pattern(regexp = "[0-9]{4}[0-3]{1}[0-9]{1}[0-9]{5}",
            message = "This is not a pesel string.")
    private String login;

    @Email(message = "Field 'email': Provided string is not an email.")
    private String email;

    @NotNull(message = "Field 'wardId' cannot be null.")
    @Min(value = 1, message = "Field 'wardId' must be greater than 0")
    private Long wardId;

    @NotNull(message = "Field 'dietId' cannot be null.")
    @Min(value = 1, message = "Field 'dietId' must be greater than 0")
    private Long dietId;
}
