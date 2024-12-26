package by.modsen.priceservice.dto.response;

import by.modsen.commonmodule.enumeration.CarCategory;

import java.math.BigDecimal;

public record TariffResponse(
        Long id,
        CarCategory carCategory,
        BigDecimal costPerKm
) {}