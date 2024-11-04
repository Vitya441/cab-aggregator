package by.modsen.passengerservice.dto.request;

import by.modsen.passengerservice.entity.enums.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_EMAIL_INVALID;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_EMAIL_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_FIRSTNAME_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_LASTNAME_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PASSWORD_MIN_SIZE;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PASSWORD_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PHONE_INVALID_FORMAT;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_PHONE_NOT_EMPTY;
import static by.modsen.passengerservice.utils.MessageUtils.VALIDATION_USERNAME_NOT_EMPTY;
import static by.modsen.passengerservice.utils.PatternUtils.REGEX_EMAIL;
import static by.modsen.passengerservice.utils.PatternUtils.REGEX_PHONE;

public record PassengerUpdateDto(
        @NotBlank(message = VALIDATION_USERNAME_NOT_EMPTY)
        String username,

        @NotBlank(message = VALIDATION_EMAIL_NOT_EMPTY)
        @Email(message = VALIDATION_EMAIL_INVALID, regexp = REGEX_EMAIL)
        String email,

        @NotBlank(message = VALIDATION_PASSWORD_NOT_EMPTY)
        @Size(min = 8, message = VALIDATION_PASSWORD_MIN_SIZE)
        String password,

        @NotBlank(message = VALIDATION_FIRSTNAME_NOT_EMPTY)
        String firstName,

        @NotBlank(message = VALIDATION_LASTNAME_NOT_EMPTY)
        String lastName,

        @NotBlank(message = VALIDATION_PHONE_NOT_EMPTY)
        @Pattern(regexp = REGEX_PHONE,
                message = VALIDATION_PHONE_INVALID_FORMAT)
        String phone,

        @NotNull
        LocalDate birthDate,

        @NotNull
        PaymentMethod preferredPaymentMethod
) {}