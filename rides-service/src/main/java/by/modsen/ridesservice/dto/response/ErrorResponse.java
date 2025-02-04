package by.modsen.ridesservice.dto.response;

public record ErrorResponse(
        int statusCode,
        String message
) {}