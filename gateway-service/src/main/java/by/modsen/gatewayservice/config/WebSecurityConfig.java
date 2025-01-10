package by.modsen.gatewayservice.config;

import by.modsen.gatewayservice.util.RouteConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers(RouteConstants.PUBLIC_ROUTES.toArray(new String[0])).permitAll()
                        .pathMatchers(HttpMethod.GET, RouteConstants.PASSENGER_ROUTES.toArray(new String[0])).hasRole("PASSENGER")
                        .pathMatchers(HttpMethod.GET, RouteConstants.DRIVER_ROUTES.toArray(new String[0])).hasRole("DRIVER")

                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .build();
    }
}