package by.modsen.passengerservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_FIRSTNAME_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_LASTNAME_NOT_EMPTY;

@Builder
public record PassengerCreateDto(
        @NotBlank(message = VALIDATION_FIRSTNAME_NOT_EMPTY)
        String firstName,

        @NotBlank(message = VALIDATION_LASTNAME_NOT_EMPTY)
        String lastName
) {}