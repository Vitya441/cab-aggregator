package by.modsen.keycloakservice.service;

import by.modsen.keycloakservice.dto.NewUserDto;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface UserService {

    String createUser(NewUserDto newUserDto);

    void deleteUser(String userId);

    UserResource getUser(String userId);

    List<RoleRepresentation> getUserRoles(String userId);

    List<GroupRepresentation> getUserGroups(String userId);
}