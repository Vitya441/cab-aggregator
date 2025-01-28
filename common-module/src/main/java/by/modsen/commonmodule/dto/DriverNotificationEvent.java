package by.modsen.commonmodule.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record DriverNotificationEvent(
        Long driverId,
        Long rideId,
        String message,
        String pickupAddress,
        String destinationAddress,
        BigDecimal cost
) {}