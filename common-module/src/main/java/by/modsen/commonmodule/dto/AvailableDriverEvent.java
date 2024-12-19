package by.modsen.commonmodule.dto;

import lombok.Builder;

@Builder
public record AvailableDriverEvent(
        Long rideId,
        Long driverId
) {}