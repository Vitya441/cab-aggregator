package by.modsen.keycloakservice.service.impl;

import by.modsen.keycloakservice.config.KeycloakConfig;
import by.modsen.keycloakservice.dto.LoginRequestDto;
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
    public AccessTokenResponse getJwt(LoginRequestDto loginRequestDto) {
        Keycloak userKeycloak = keycloakConfig.userKeycloak(
                loginRequestDto.username(),
                loginRequestDto.password()
        );
        return userKeycloak.tokenManager().getAccessToken();
    }

    @Override
    public void registerUser(NewUserDto newUserDto) {
        if (newUserDto.userRole() == UserRole.PASSENGER) {
            createPassenger(newUserDto);
        } else if (newUserDto.userRole() == UserRole.DRIVER) {
            createDriver(newUserDto);
        } else {
            throw new RuntimeException("Unsupported user role: " + newUserDto.userRole());
        }

    }

    private void createDriver(NewUserDto newUserDto) {
        String userId = userService.createUser(newUserDto);
        roleService.assignRole(userId, UserRole.DRIVER.getRoleName());
    }

    private void createPassenger(NewUserDto newUserDto) {
        String userId = userService.createUser(newUserDto);
        roleService.assignRole(userId, UserRole.PASSENGER.getRoleName());
    }


}
