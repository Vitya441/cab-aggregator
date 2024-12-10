package by.modsen.commonmodule.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PassengerDto(
        long id,
        String firstName,
        String lastName,
        String phone,
        LocalDate birthDate,
        BigDecimal balance,
        Double averageRating
) {}