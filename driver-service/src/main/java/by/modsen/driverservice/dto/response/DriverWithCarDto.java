package by.modsen.driverservice.dto.response;

import by.modsen.commonmodule.enumeration.DriverStatus;
import lombok.Builder;

@Builder
public record DriverWithCarDto(
        long id,
        String customerId,
        CarDto car,
        String firstName,
        String lastName,
        String phone,
        DriverStatus status
) {}