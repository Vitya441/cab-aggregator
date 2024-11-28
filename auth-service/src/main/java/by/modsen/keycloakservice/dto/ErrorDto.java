package by.modsen.keycloakservice.dto;

public record ErrorDto(
        int statusCode,
        String message
) {}