package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.enums.PaymentMethod;

import java.time.LocalDate;

public record PassengerCreateDto(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phone,
        LocalDate birthDate,
        PaymentMethod preferredPaymentMethod
) {}