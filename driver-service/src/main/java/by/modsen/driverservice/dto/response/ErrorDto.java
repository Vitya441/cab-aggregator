package by.modsen.driverservice.dto.response;

public record ErrorDto(
        int statusCode,
        String message
) {}