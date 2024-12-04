package by.modsen.driverservice.dto.request;

import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DriverCreateDto(
        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_FIRSTNAME)
        @Size(max = 50, message = ExceptionMessageKeyConstants.VALIDATION_FIRSTNAME_SIZE)
        String firstName,

        @NotBlank(message = ExceptionMessageKeyConstants.VALIDATION_LASTNAME)
        @Size(max = 50, message = ExceptionMessageKeyConstants.VALIDATION_LASTNAME_SIZE)
        String lastName
) {}