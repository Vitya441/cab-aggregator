package by.modsen.ridesservice.dto.response;

public record DriverDto(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String status
) {}