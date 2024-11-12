package by.modsen.keycloakservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    private static final String REALM_ACCESS_CLAIM = "realm_access";

    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";

    public JwtAuthConverter() {
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        String principalClaimName = getPrincipalClaimName(jwt);

        return new JwtAuthenticationToken(jwt, authorities, principalClaimName);
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;

        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);

        Collection<String> allRoles = new ArrayList<>();
        allRoles.addAll(getRealmRoles(realmAccess));
        allRoles.addAll(getResourceRoles(resourceAccess));

        log.info("Extracted roles: " + allRoles);

        return allRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    private Collection<String> getRealmRoles(Map<String, Object> realmAccess) {
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            return (Collection<String>) realmAccess.get("roles");
        }
        return Collections.emptyList();
    }

    private Collection<String> getResourceRoles(Map<String, Object> resourceAccess) {
        if (resourceAccess != null && resourceAccess.get("account") != null) {
            Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
            if (account.containsKey("roles")) {
                return (Collection<String>) account.get("roles");
            }
        }
        return Collections.emptyList();
    }
}