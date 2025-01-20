package by.modsen.authservice.config;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.keycloak")
@Getter @Setter
public class KeycloakProperties {

    private String adminClientId;
    private String adminClientSecret;
    private String userClientId;
    private String realm;
    private String serverUrl;

    public final String REALMS_PATH = "/realms/";
    public final String LOGOUT_PATH = "/protocol/openid-connect/logout";
    public final String TOKEN_PATH = "/protocol/openid-connect/token";
    public final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

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