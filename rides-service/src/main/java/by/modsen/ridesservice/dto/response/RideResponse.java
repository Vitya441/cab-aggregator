package by.modsen.ridesservice.dto.response;

import by.modsen.commonmodule.enumeration.PaymentMethod;
import by.modsen.commonmodule.enumeration.RideStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
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
        LocalDateTime startTime,
        LocalDateTime endTime
) {}