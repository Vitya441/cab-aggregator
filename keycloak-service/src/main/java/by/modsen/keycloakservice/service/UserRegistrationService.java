package by.modsen.keycloakservice.service;

import by.modsen.keycloakservice.dto.LoginRequestDto;
import by.modsen.keycloakservice.dto.NewUserDto;
import org.keycloak.representations.AccessTokenResponse;

public interface UserRegistrationService {

    AccessTokenResponse getJwt(LoginRequestDto authRequest);

    void registerUser(NewUserDto newUserDto);

}
