package by.modsen.priceservice.dto.request;

import by.modsen.commonmodule.enumeration.CarCategory;

import java.math.BigDecimal;

public record TariffRequest(
        CarCategory carCategory,
        BigDecimal costPerKm
) {}