package by.modsen.gatewayservice.config;

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
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.modsen.gatewayservice.util.SecurityConstants.ACCOUNT_KEY;
import static by.modsen.gatewayservice.util.SecurityConstants.REALM_ACCESS_CLAIM;
import static by.modsen.gatewayservice.util.SecurityConstants.RESOURCE_ACCESS_CLAIM;
import static by.modsen.gatewayservice.util.SecurityConstants.ROLES_KEY;

@Slf4j
@Component
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;

    public JwtAuthConverter() {
        this.jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        String principalClaimName = getPrincipalClaimName(jwt);

        return Mono.just(new JwtAuthenticationToken(jwt, authorities, principalClaimName));
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
        Map<String, Object> resourceAccess = jwt.getClaim(RESOURCE_ACCESS_CLAIM);

        Collection<String> allRoles = new ArrayList<>();
        Collection<String> realmRoles = getRealmRoles(realmAccess);
        Collection<String> resourceRoles = getResourceRoles(resourceAccess);

        allRoles.addAll(realmRoles);
        allRoles.addAll(resourceRoles);

        log.info("Extracted roles: " + allRoles);

        return allRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    private Collection<String> getRealmRoles(Map<String, Object> realmAccess) {
        if (realmAccess != null && realmAccess.containsKey(ROLES_KEY)) {
            return (Collection<String>) realmAccess.get(ROLES_KEY);
        }
        return Collections.emptyList();
    }

    private Collection<String> getResourceRoles(Map<String, Object> resourceAccess) {
        if (resourceAccess != null && resourceAccess.get(ACCOUNT_KEY) != null) {
            Map<String, Object> account = (Map<String, Object>) resourceAccess.get(ACCOUNT_KEY);
            if (account.containsKey(ROLES_KEY)) {
                return (Collection<String>) account.get(ROLES_KEY);
            }
        }
        return Collections.emptyList();
    }
}