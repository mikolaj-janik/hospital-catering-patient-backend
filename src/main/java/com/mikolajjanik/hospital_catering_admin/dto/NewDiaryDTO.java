package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewDiaryDTO {

    @NotNull(message = "Field 'dietId' cannot be null.")
    @Min(value = 1, message = "Field 'dietId' must be greater than 0")
    private Long dietId;

    @NotNull(message = "Field 'breakfastId' cannot be null.")
    @Min(value = 1, message = "Field 'breakfastId' must be greater than 0")
    private Long breakfastId;

    @NotNull(message = "Field 'lunchId' cannot be null.")
    @Min(value = 1, message = "Field 'lunchId' must be greater than 0")
    private Long lunchId;

    @NotNull(message = "Field 'supperId' cannot be null.")
    @Min(value = 1, message = "Field 'supperId' must be greater than 0")
    private Long supperId;

    @NotBlank(message = "Field 'date' cannot be empty.")
    @NotNull(message = "Field 'date' cannot be null.")
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[1,2])-(19|20)\\d{2}", message = "Field 'date' must be in the format DD-MM-YYYY")
    private String date;

    private String repeatFor;

    private String repeatUntil;
}
