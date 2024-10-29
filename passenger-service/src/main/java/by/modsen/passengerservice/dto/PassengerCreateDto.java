package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.enums.PaymentMethod;

public record PassengerCreateDto(
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phone,
        String birthDate,
        PaymentMethod preferredPaymentMethod
) {}