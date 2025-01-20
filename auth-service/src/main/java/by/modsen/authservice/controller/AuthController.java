package by.modsen.authservice.controller;

import by.modsen.authservice.dto.LoginRequest;
import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.UserRole;
import by.modsen.authservice.service.UserRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(
            @RequestBody
            NewUserDto newUserDto,
            @RequestParam(name = "role")
            UserRole userRole
    ) throws JsonProcessingException {
        userRegistrationService.registerUser(newUserDto, userRole);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userRegistrationService.getJwt(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
        userRegistrationService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update-token")
    public ResponseEntity<AccessTokenResponse> updateToken(@RequestParam String refreshToken) {
        AccessTokenResponse newAccessToken = userRegistrationService.updateToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}