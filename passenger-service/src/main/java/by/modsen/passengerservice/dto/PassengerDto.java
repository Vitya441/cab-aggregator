package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

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
        List<Integer> ratings, //todo: Возвращать среднюю оценку, а не список
        Instant createdAt,
        Instant updatedAt
) {}
