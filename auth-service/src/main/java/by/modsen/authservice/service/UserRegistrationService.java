package by.modsen.authservice.service;

import by.modsen.authservice.dto.LoginRequest;
import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.keycloak.representations.AccessTokenResponse;

public interface UserRegistrationService {

    AccessTokenResponse getJwt(LoginRequest loginRequest);

    void registerUser(NewUserDto newUserDto, UserRole userRole) throws JsonProcessingException;

    void logout(String refreshToken);

    AccessTokenResponse updateToken(String refreshToken);
}