package by.modsen.authservice.dto;

public record LoginRequest(
        String username,
        String password
) {}