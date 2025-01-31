package by.modsen.passengerservice.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record PassengerDto(
        long id,
        String customerId,
        String firstName,
        String lastName,
        String phone,
        LocalDate birthDate,
        BigDecimal balance,
        Double averageRating
) {}