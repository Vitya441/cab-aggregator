package by.modsen.promocodeservice.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {}