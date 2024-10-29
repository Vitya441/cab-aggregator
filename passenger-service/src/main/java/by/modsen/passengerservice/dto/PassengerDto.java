package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PassengerDto(
        Long id,
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phone,
        String birthDate,
        PaymentMethod preferredPaymentMethod,
        BigDecimal balance,
        List<Integer> ratings,
        Instant createdAt,
        Instant updatedAt
) {}
