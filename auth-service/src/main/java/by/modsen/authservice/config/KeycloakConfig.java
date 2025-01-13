package by.modsen.authservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .clientId(keycloakProperties.getAdminClientId())
                .clientSecret(keycloakProperties.getAdminClientSecret())
                .grantType("client_credentials")
                .realm(keycloakProperties.getRealm())
                .serverUrl(keycloakProperties.getServerUrl())
                .build();
    }

//    public Keycloak userKeycloak(String username, String password) {
//        return KeycloakBuilder.builder()
//                .clientId(keycloakProperties.getUserClientId())
//                .realm(keycloakProperties.getRealm())
//                .serverUrl(keycloakProperties.getServerUrl())
//                .grantType("password")
//                .username(username)
//                .password(password)
//                .build();
//    }

}