package by.modsen.keycloakservice.service;

import by.modsen.keycloakservice.dto.LoginRequest;
import by.modsen.keycloakservice.dto.NewUserDto;
import by.modsen.keycloakservice.dto.UserRole;
import org.keycloak.representations.AccessTokenResponse;

public interface UserRegistrationService {

    AccessTokenResponse getJwt(LoginRequest loginRequest);

    void registerUser(NewUserDto newUserDto, UserRole userRole);
}