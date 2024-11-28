package by.modsen.passengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PHONE_INVALID_FORMAT;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PHONE_NOT_EMPTY;
import static by.modsen.passengerservice.utils.PatternUtils.REGEX_PHONE;

public record PassengerUpdateDto(
        @NotBlank(message = VALIDATION_PHONE_NOT_EMPTY)
        @Pattern(regexp = REGEX_PHONE,
                message = VALIDATION_PHONE_INVALID_FORMAT)
        String phone
) {}