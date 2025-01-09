package by.modsen.driverservice.dto.response;

public record DriverDto(
        long id,
        String customerId,
        String firstName,
        String lastName,
        String phone,
        String status
) {}