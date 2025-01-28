package by.modsen.authservice.service.impl;

import by.modsen.authservice.config.KeycloakProperties;
import by.modsen.authservice.dto.LoginRequest;
import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.UserRole;
import by.modsen.authservice.entity.InboxMessage;
import by.modsen.authservice.repository.InboxRepository;
import by.modsen.authservice.service.UserRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final InboxRepository inboxRepository;
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

    @Transactional
    @Override
    public void registerUser(NewUserDto newUserDto, UserRole userRole) {
        InboxMessage inboxMessage = getInboxMessage(newUserDto, userRole);
        inboxRepository.save(inboxMessage);
    }

    @Override
    public void logout(String refreshToken) {
        String logoutUrl = keycloakProperties.getServerUrl() +
                keycloakProperties.REALMS_PATH + keycloakProperties.getRealm() +
                keycloakProperties.LOGOUT_PATH;

        HttpEntity<MultiValueMap<String, String>> request = createHttpEntity(refreshToken);
        ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, request, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to logout: " + response.getBody());
        }
    }

    @Override
    public AccessTokenResponse updateToken(String refreshToken) {
        String tokenUrl = keycloakProperties.getServerUrl() +
                keycloakProperties.REALMS_PATH + keycloakProperties.getRealm() +
                keycloakProperties.TOKEN_PATH;

        HttpEntity<MultiValueMap<String, String>> request = createHttpEntity(refreshToken);
        ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(tokenUrl, request, AccessTokenResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to refresh token: " + response.getBody());
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createHttpEntity(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", keycloakProperties.getUserClientId());
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        return new HttpEntity<>(body, headers);
    }

    private InboxMessage getInboxMessage(NewUserDto newUserDto, UserRole userRole) {
        ObjectMapper objectMapper = new ObjectMapper();
        String payload;
        try {
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("user", newUserDto);
            node.put("role", userRole.name());
            payload = objectMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize NewUserDto and role", e);
        }

        InboxMessage inboxMessage = new InboxMessage();
        inboxMessage.setPayload(payload);

        return inboxMessage;
    }
}