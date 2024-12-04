package by.modsen.keycloakservice.dto;

public record LoginRequest(
        String username,
        String password
) {}