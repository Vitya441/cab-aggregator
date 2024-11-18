package by.modsen.driverservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CarCreateDto(
        @NotBlank(message = "Licence number is required")
        @Size(max = 20, message = "Licence number can't be grater than 20 characters")
        String licenseNumber,

        @NotBlank(message = "Color is required")
        @Size(max = 30, message = "Color can't be greater than 30 characters")
        String color,

        @Min(value = 1, message = "Seats must be at least 1")
        @Max(value = 9, message = "Seats can't be greater than 9")
        int seats,

        @NotBlank(message = "Brand is required")
        @Size(max = 50, message = "Brand can't be greater than 50 characters")
        String brand,

        @NotBlank(message = "Model is required")
        @Size(max = 50, message = "Model can't be greater than 50 characters")
        String model,

        @NotBlank(message = "Category is required")
        @Size(max = 30, message = "Category can't be greater than 30 characters")
        String category
) {}
