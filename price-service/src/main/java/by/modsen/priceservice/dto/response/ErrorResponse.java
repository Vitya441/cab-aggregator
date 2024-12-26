package by.modsen.priceservice.dto.response;

public record ErrorResponse(
        int statusCode,
        String message
) {}