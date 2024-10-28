package by.modsen.passengerservice.dto;

import by.modsen.passengerservice.entity.PersonalInfo;
import by.modsen.passengerservice.entity.enums.PaymentMethod;

public record PassengerCreateDto(
        String username,
        String password,
        String email,
        PersonalInfo info,  // Убедитесь, что это поле не null
        PaymentMethod preferredPaymentMethod
) {}