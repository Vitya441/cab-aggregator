package by.modsen.authservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewUserDto(
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {}