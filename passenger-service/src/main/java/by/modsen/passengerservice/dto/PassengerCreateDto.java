package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.enums.PaymentMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PassengerCreateDto(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email should be valid", regexp = "^(.+)@(\\S+)$")
        String email,

        @NotBlank(message = "Password cannot be empty")
        @Size(min = 8, message = "Minimum 8 symbols in password is required")
        String password,

        @NotBlank(message = "Firstname cannot be empty")
        String firstName,

        @NotBlank(message = "Lastname cannot be empty")
        String lastName,

        @NotBlank(message = "Phone cannot be empty")
        @Pattern(regexp = "^\\+\\d+$",
                message = "Invalid phone number format")
        String phone,

        LocalDate birthDate,

        PaymentMethod preferredPaymentMethod
) {}