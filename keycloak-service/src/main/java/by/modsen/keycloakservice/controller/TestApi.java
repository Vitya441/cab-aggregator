package by.modsen.keycloakservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Test Class to check functionality of authorization
 *
 */

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestApi {

    @GetMapping("/passenger-role")
    public ResponseEntity<?> testForPassengerRole() {
        return ResponseEntity.ok("Hello Passenger!");
    }

    @GetMapping("/driver-role")
    public ResponseEntity<?> testForDriverRole() {
        return ResponseEntity.ok("Hello driver!");
    }
}