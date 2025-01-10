package by.modsen.authservice.dto;

public record ErrorDto(
        int statusCode,
        String message
) {}