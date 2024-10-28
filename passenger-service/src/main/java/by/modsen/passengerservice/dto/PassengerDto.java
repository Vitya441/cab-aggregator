package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.PersonalInfo;
import by.modsen.passengerservice.entity.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PassengerDto(Long id,
                           String username,
                           String password,
                           String email,
                           PersonalInfo info,
                           PaymentMethod preferredPaymentMethod,
                           BigDecimal balance,
                           List<Integer> ratings,
//                           Double averageRating,
                           Instant createdAt,
                           Instant updatedAt
) {}
