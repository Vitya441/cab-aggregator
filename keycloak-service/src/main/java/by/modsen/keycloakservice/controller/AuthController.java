package by.modsen.keycloakservice.controller;

import by.modsen.keycloakservice.dto.LoginRequest;
import by.modsen.keycloakservice.dto.NewUserDto;
import by.modsen.keycloakservice.dto.UserRole;
import by.modsen.keycloakservice.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(
            @RequestBody NewUserDto newUserDto,
            @RequestParam(name = "role") UserRole userRole
    ) {
        userRegistrationService.registerUser(newUserDto, userRole);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userRegistrationService.getJwt(loginRequest));

    }
}