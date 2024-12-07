package by.modsen.commonmodule.dto;

import by.modsen.commonmodule.enumeration.PaymentMethod;
import by.modsen.commonmodule.enumeration.RideStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record RideResponse(
        Long id,
        Long passengerId,
        Long driverId,
        String pickupAddress,
        String destinationAddress,
        BigDecimal estimatedCost,
        BigDecimal actualCost,
        RideStatus status,
        PaymentMethod paymentMethod,
        Instant startTime,
        Instant endTime
) {}
