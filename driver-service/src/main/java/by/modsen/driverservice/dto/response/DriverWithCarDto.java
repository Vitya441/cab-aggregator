package by.modsen.driverservice.dto.response;

public record DriverWithCarDto(
        long id,
        CarDto car,
        String firstName,
        String lastName,
        String phone,
        String status
) {}