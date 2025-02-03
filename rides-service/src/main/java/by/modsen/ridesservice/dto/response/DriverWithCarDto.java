package by.modsen.ridesservice.dto.response;

import by.modsen.commonmodule.enumeration.DriverStatus;

public record DriverWithCarDto(
        long id,
        String customerId,
        CarDto car,
        String firstName,
        String lastName,
        String phone,
        DriverStatus status
) {}