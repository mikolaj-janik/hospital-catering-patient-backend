package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateMealDTO {
    @NotBlank(message = "Field 'id' cannot be empty.")
    @NotNull(message = "Field 'id' cannot be null.")
    private String id;
    @NotBlank(message = "Field 'dietId' cannot be empty.")
    @NotNull(message = "Field 'dietId' cannot be null.")
    private String dietId;

    @NotBlank(message = "Field 'name' cannot be empty.")
    @NotNull(message = "Field 'name' cannot be null.")
    @Size(max = 255, message = "Field 'name' cannot exceed 255 characters.")
    private String name;

    @NotBlank(message = "Field 'description' cannot be empty.")
    @NotNull(message = "Field 'description' cannot be null.")
    @Size(max = 1020, message = "Field 'description' cannot exceed 1020 characters.")
    private String description;

    @NotBlank(message = "Field 'price' cannot be empty.")
    @NotNull(message = "Field 'price' cannot be null.")
    private String price;

    @NotBlank(message = "Field 'type' cannot be empty.")
    @NotNull(message = "Field 'type' cannot be null.")
    private String type;

    @NotBlank(message = "Field 'calories' cannot be empty.")
    @NotNull(message = "Field 'calories' cannot be null.")
    private String calories;

    @NotBlank(message = "Field 'protein' cannot be empty.")
    @NotNull(message = "Field 'protein' cannot be null.")
    private String protein;

    @NotBlank(message = "Field 'carbohydrates' cannot be empty.")
    @NotNull(message = "Field 'carbohydrates' cannot be null.")
    private String carbohydrates;

    @NotBlank(message = "Field 'fats' cannot be empty.")
    @NotNull(message = "Field 'fats' cannot be null.")
    private String fats;
}
