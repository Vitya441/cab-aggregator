package by.modsen.ridesservice.dto.response;

import by.modsen.commonmodule.enumeration.PaymentMethod;
import by.modsen.commonmodule.enumeration.RideStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record RideWithDriverResponse(
        Long id,
        DriverWithCarDto driver,
        String pickupAddress,
        String destinationAddress,
        BigDecimal estimatedCost,
        BigDecimal actualCost,
        RideStatus status,
        PaymentMethod paymentMethod,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}