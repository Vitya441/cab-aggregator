package by.modsen.keycloakservice.dto;

public record NewUserDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {}