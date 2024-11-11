package by.modsen.keycloakservice.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${app.keycloak.admin.clientId}")
    private String adminClientId;

    @Value("${app.keycloak.user.clientId}")
    private String userClientId;

    @Value("${app.keycloak.admin.clientSecret}")
    private String clientSecret;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.serverUrl}")
    private String serverUrl;

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
