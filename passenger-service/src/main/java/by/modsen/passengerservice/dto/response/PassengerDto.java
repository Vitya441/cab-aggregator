package by.modsen.passengerservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

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