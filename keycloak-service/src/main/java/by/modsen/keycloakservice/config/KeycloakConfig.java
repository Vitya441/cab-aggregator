package by.modsen.keycloakservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    private final String adminClientId;

    private final String userClientId;

    private final String clientSecret;

    private final String realm;

    private final String serverUrl;

    public KeycloakConfig(
            @Value("${app.keycloak.admin.clientId}") String adminClientId,
            @Value("${app.keycloak.user.clientId}") String userClientId,
            @Value("${app.keycloak.admin.clientSecret}") String clientSecret,
            @Value("${app.keycloak.realm}") String realm,
            @Value("${app.keycloak.serverUrl}") String serverUrl) {
        this.adminClientId = adminClientId;
        this.userClientId = userClientId;
        this.clientSecret = clientSecret;
        this.realm = realm;
        this.serverUrl = serverUrl;
    }

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .clientSecret(clientSecret)
                .clientId(adminClientId)
                .grantType("client_credentials")
                .realm(realm)
                .serverUrl(serverUrl)
                .build();
    }

    public Keycloak userKeycloak(String username, String password) {
        return KeycloakBuilder.builder()
                .clientId(userClientId)
                .realm(realm)
                .serverUrl(serverUrl)
                .grantType("password")
                .username(username)
                .password(password)
                .build();
    }
}