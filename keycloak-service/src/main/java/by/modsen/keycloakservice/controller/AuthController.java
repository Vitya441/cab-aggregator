package by.modsen.keycloakservice.controller;

import by.modsen.keycloakservice.dto.LoginRequestDto;
import by.modsen.keycloakservice.dto.NewUserDto;
import by.modsen.keycloakservice.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRegistrationService userRegistrationService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody NewUserDto newUserDto) {
        userRegistrationService.registerUser(newUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto authRequest) {
        return ResponseEntity.ok(userRegistrationService.getJwt(authRequest));
    }




}
