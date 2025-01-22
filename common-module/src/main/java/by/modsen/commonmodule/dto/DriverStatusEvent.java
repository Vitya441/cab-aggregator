package by.modsen.commonmodule.dto;

import by.modsen.commonmodule.enumeration.DriverStatus;
import lombok.Builder;

@Builder
public record DriverStatusEvent(
        Long rideId,
        Long driverId,
        DriverStatus driverStatus
) {}