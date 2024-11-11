package by.modsen.keycloakservice.service.impl;

import by.modsen.keycloakservice.service.GroupService;
import by.modsen.keycloakservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;
    private final UserService userService;

    @Override
    public void assignGroup(String userId, String groupId) {
        UserResource user = userService.getUser(userId);
        user.joinGroup(groupId);
    }

    @Override
    public void deleteGroupFromUser(String userId, String groupId) {
        UserResource user = userService.getUser(userId);
        user.leaveGroup(groupId);
    }
}
