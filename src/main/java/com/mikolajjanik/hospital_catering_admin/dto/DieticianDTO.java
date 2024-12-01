package com.mikolajjanik.hospital_catering_admin.dto;

import com.mikolajjanik.hospital_catering_admin.entity.Ward;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class DieticianDTO {

    @NotNull(message = "Field 'name' cannot be null.")
    @NotBlank(message = "Field 'name' cannot be empty.")
    private String name;

    @NotNull(message = "Field 'surname' cannot be null.")
    @NotBlank(message = "Field 'surname' cannot be empty.")
    private String surname;

    @NotNull(message = "Field 'email' cannot be null.")
    @NotBlank(message = "Field 'email' cannot be empty.")
    @Email(message = "Field 'email': Provided string is not an email.")
    private String email;

    @NotNull(message = "Field 'defaultPassword' cannot be null.")
    @NotBlank(message = "Field 'defaultPassword' cannot be empty.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$",
            message = "Password must contain one digit from 1 to 9, " +
            "one lowercase letter, one uppercase letter, one special character, " +
            "no space, and it must be 8-16 characters long")
    private String defaultPassword;

    @NotNull(message = "Field 'hospitalId' cannot be null.")
    @Min(value = 1, message = "Field 'hospitalId' must be greater than 0")
    private Long hospitalId;

    private List<Ward> wards;
}
