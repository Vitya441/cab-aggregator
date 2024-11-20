package by.modsen.driverservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DriverCreateDto(
        @NotBlank(message = "Firstname is required")
        @Size(max = 50, message = "Firstname can't be greater than 50")
        String firstName,

        @NotBlank(message = "Lastname is required")
        @Size(max = 50, message = "Lastname can't be greater than 50")
        String lastName,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\+?[1-9]\\d{1,14}$",
                message = "Phone number must be a valid international phone number"
        )
        String phone
) {}
