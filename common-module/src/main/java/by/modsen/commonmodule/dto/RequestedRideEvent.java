package by.modsen.commonmodule.dto;

import lombok.Builder;

@Builder
public record RequestedRideEvent (
        Long rideId,
        String pickupAddress
) {}