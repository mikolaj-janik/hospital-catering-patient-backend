package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class NewUserDTO {

    @NotNull(message = "Field 'email' cannot be null.")
    @NotBlank(message = "Field 'email' cannot be empty.")
    @Email(message = "Field 'email': Provided string is not an email.")
    private String email;

    @NotNull(message = "Field 'password' cannot be null.")
    @NotBlank(message = "Field 'password' cannot be empty.")
    private String password;

    @NotNull(message = "Field 'repeatedPassword' cannot be null.")
    @NotBlank(message = "Field 'repeatedPassword' cannot be empty.")
    private String repeatedPassword;

    @NotNull(message = "Field 'name' cannot be null.")
    @NotBlank(message = "Field 'name' cannot be empty.")
    private String name;

    @NotNull(message = "Field 'surname' cannot be null.")
    @NotBlank(message = "Field 'surname' cannot be empty.")
    private String surname;

    @NotNull(message = "Field 'profilePicture' cannot be null.")
    @NotBlank(message = "Field 'profilePicture' cannot be empty.")
    private String profilePicture;

}
