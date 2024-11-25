package by.modsen.driverservice.dto.request;

import by.modsen.driverservice.entity.enums.CarCategory;
import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CarCreateDto(
        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_LICENCE_NUMBER)
        @Size(max = 20, message = ExceptionMessageKeyConstants.VALIDATION_LICENCE_NUMBER_SIZE)
        String licenseNumber,

        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_COLOR)
        @Size(max = 30, message = ExceptionMessageKeyConstants.VALIDATION_COLOR_SIZE)
        String color,

        @Min(value = 1, message = ExceptionMessageKeyConstants.VALIDATION_SEATS_MIN)
        @Max(value = 9, message = ExceptionMessageKeyConstants.VALIDATION_SEATS_MAX)
        int seats,

        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_BRAND)
        @Size(max = 50, message = ExceptionMessageKeyConstants.VALIDATION_BRAND_SIZE)
        String brand,

        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_MODEL)
        @Size(max = 50, message = ExceptionMessageKeyConstants.VALIDATION_MODEL_SIZE)
        String model,

        CarCategory category
) {}