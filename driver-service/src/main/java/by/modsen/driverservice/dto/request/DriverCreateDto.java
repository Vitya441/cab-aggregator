package by.modsen.driverservice.dto.request;

import by.modsen.driverservice.util.MessageUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DriverCreateDto(
        @NotBlank(message = MessageUtil.VALIDATION_FIRSTNAME)
        @Size(max = 50, message = MessageUtil.VALIDATION_FIRSTNAME_SIZE)
        String firstName,

        @NotBlank(message = MessageUtil.VALIDATION_LASTNAME)
        @Size(max = 50, message = MessageUtil.VALIDATION_LASTNAME_SIZE)
        String lastName,

        @NotBlank(message = MessageUtil.VALIDATION_PHONE)
        @Pattern(
                regexp = MessageUtil.VALIDATION_PHONE_REGEX,
                message = MessageUtil.VALIDATION_PHONE_REGEX_MESSAGE
        )
        String phone
) {}