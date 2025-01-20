package by.modsen.authservice.dto;

public record PayloadWrapper(
        NewUserDto user,
        UserRole role
) {}