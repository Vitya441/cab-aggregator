package by.modsen.authservice.controller;

import by.modsen.authservice.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PutMapping("/{groupId}/assign/users/{userId}")
    public ResponseEntity<?> assignGroup(@PathVariable String userId, @PathVariable String groupId) {
        groupService.assignGroup(userId, groupId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/remove/users/{userId}")
    public ResponseEntity<?> unAssignGroup(@PathVariable String userId, @PathVariable String groupId) {
        groupService.deleteGroupFromUser(userId, groupId);
        return ResponseEntity.noContent().build();
    }
}