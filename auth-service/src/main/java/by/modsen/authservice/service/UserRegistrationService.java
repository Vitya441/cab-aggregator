package by.modsen.authservice.service;

import by.modsen.authservice.dto.LoginRequest;
import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.UserRole;
import org.keycloak.representations.AccessTokenResponse;

public interface UserRegistrationService {

    AccessTokenResponse getJwt(LoginRequest loginRequest);

    void registerUser(NewUserDto newUserDto, UserRole userRole);
}