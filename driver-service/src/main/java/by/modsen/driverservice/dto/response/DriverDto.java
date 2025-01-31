package by.modsen.driverservice.dto.response;

import by.modsen.commonmodule.enumeration.DriverStatus;
import lombok.Builder;

@Builder
public record DriverDto(
        long id,
        String customerId,
        String firstName,
        String lastName,
        String phone,
        DriverStatus status
) {}