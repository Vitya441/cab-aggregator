package by.modsen.driverservice.dto.request;

import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DriverUpdateDto(
        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_PHONE)
        @Pattern(
                regexp = ExceptionMessageKeyConstants.VALIDATION_PHONE_REGEX,
                message = ExceptionMessageKeyConstants.VALIDATION_PHONE_REGEX_MESSAGE
        )
        String phone
) {}