package by.modsen.driverservice.dto.response;

public record DriverDto(
        long id,
        String firstName,
        String lastName,
        String phone,
        String status
) {}
