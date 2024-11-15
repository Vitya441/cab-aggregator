package by.modsen.keycloakservice.service.impl;

import by.modsen.keycloakservice.config.KeycloakConfig;
import by.modsen.keycloakservice.dto.LoginRequest;
import by.modsen.keycloakservice.dto.NewUserDto;
import by.modsen.keycloakservice.dto.UserRole;
import by.modsen.keycloakservice.service.RoleService;
import by.modsen.keycloakservice.service.UserRegistrationService;
import by.modsen.keycloakservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserService userService;
    private final RoleService roleService;
    private final KeycloakConfig keycloakConfig;

    @Override
    public AccessTokenResponse getJwt(LoginRequest loginRequest) {
        Keycloak userKeycloak = keycloakConfig.userKeycloak(
                loginRequest.username(),
                loginRequest.password()
        );

        return userKeycloak.tokenManager().getAccessToken();
    }

    @Override
    public void registerUser(NewUserDto newUserDto, UserRole userRole) {
        String userId = userService.createUser(newUserDto);
        roleService.assignRole(userId, userRole.name());
    }
}