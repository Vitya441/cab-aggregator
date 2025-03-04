package by.modsen.promocodeservice.dto;

import by.modsen.promocodeservice.util.ExceptionMessageConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PromoCodeRequest(

        @NotBlank(message = ExceptionMessageConstants.VALIDATION_PROMOCODE_NAME_BLANK)
        @Size(max = 100, message = ExceptionMessageConstants.VALIDATION_PROMOCODE_NAME_SIZE)
        String name,

        @NotBlank(message = ExceptionMessageConstants.VALIDATION_PROMOCODE_CODE_BLANK)
        @Pattern(regexp = "^[A-Z0-9_-]{5,20}$", message = ExceptionMessageConstants.VALIDATION_PROMOCODE_CODE_PATTERN)
        String code,

        @NotNull(message = ExceptionMessageConstants.VALIDATION_PROMOCODE_COUNT_MIN)
        @Min(value = 1, message = ExceptionMessageConstants.VALIDATION_PROMOCODE_COUNT_MIN)
        Long count,

        @NotNull(message = ExceptionMessageConstants.VALIDATION_PROMOCODE_PERCENT_MIN)
        @Min(value = 0, message = ExceptionMessageConstants.VALIDATION_PROMOCODE_PERCENT_MIN)
        @Max(value = 100, message = ExceptionMessageConstants.VALIDATION_PROMOCODE_PERCENT_MAX)
        Integer percent
) {}