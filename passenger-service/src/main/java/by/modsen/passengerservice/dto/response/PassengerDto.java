package by.modsen.passengerservice.dto.response;

import by.modsen.passengerservice.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PassengerDto(
        long id,
        String username,
        String password,
        String email,
        String firstName,
        String lastName,
        String phone,
        LocalDate birthDate,
        PaymentMethod preferredPaymentMethod,
        BigDecimal balance,
        Double averageRating,
        Instant createdAt,
        Instant updatedAt
) {}