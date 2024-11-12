package by.modsen.keycloakservice.controller;

import by.modsen.keycloakservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam String roleName) {
        roleService.assignRole(userId, roleName);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove/users/{userId}")
    public ResponseEntity<?> unAssignRole(@PathVariable String userId, @RequestParam String roleName) {
        roleService.deleteFromUser(userId, roleName);
        return ResponseEntity.noContent().build();
    }
}