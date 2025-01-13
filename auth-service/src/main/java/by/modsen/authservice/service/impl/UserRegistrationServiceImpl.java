package by.modsen.authservice.service.impl;

import by.modsen.authservice.config.KeycloakProperties;
import by.modsen.authservice.dto.LoginRequest;
import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.UserRole;
import by.modsen.authservice.service.RoleService;
import by.modsen.authservice.service.UserRegistrationService;
import by.modsen.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserService userService;
    private final RoleService roleService;
    private final KafkaTemplate<String, NewUserDto> kafkaTemplate;
    @Value("${app.kafka.topics.passenger.name}")
    private String passengerTopic;
    @Value("${app.kafka.topics.driver.name}")
    private String driverTopic;
    private final RestTemplate restTemplate;
    private final KeycloakProperties keycloakProperties;

    @Override
    public AccessTokenResponse getJwt(LoginRequest loginRequest) {
        Keycloak userKeycloak = keycloakProperties.userKeycloak(
                loginRequest.username(),
                loginRequest.password()
        );

        return userKeycloak.tokenManager().getAccessToken();
    }

    @Override
    public void registerUser(NewUserDto newUserDto, UserRole userRole) {
        String userId = userService.createUser(newUserDto);
        roleService.assignRole(userId, userRole.name());
        if (userRole == UserRole.PASSENGER) {
            kafkaTemplate.send(passengerTopic, newUserDto);
        } else if (userRole == UserRole.DRIVER) {
            kafkaTemplate.send(driverTopic, newUserDto);
        }
    }

    @Override
    public void logout(String refreshToken) {
        String logoutUrl = keycloakProperties.getServerUrl() +
                "/realms/" + keycloakProperties.getRealm() +
                "/protocol/openid-connect/logout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.getUserClientId());
        body.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to logout: " + response.getBody());
        }
    }

    @Override
    public AccessTokenResponse updateToken(String refreshToken) {
        String tokenUrl = keycloakProperties.getServerUrl() + "/realms/" + keycloakProperties.getRealm() + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.getUserClientId());
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, AccessTokenResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to refresh token: " + response.getBody());
        }
    }
}