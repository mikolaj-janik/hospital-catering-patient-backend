package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DiaryDTO {
    @NotNull(message = "Field 'id' cannot be null.")
    @Min(value = 1, message = "Field 'id' must be greater than 0")
    private Long id;

    @NotNull(message = "Field 'breakfastId' cannot be null.")
    @Min(value = 1, message = "Field 'breakfastId' must be greater than 0")
    private Long breakfastId;

    @NotNull(message = "Field 'lunchId' cannot be null.")
    @Min(value = 1, message = "Field 'lunchId' must be greater than 0")
    private Long lunchId;

    @NotNull(message = "Field 'supperId' cannot be null.")
    @Min(value = 1, message = "Field 'supperId' must be greater than 0")
    private Long supperId;
}
