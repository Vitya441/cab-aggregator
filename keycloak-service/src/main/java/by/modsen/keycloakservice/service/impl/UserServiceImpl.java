package by.modsen.keycloakservice.service.impl;

import by.modsen.keycloakservice.dto.NewUserDto;
import by.modsen.keycloakservice.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${app.keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;


    @Override
    public String createUser(NewUserDto newUserDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserDto.firstName());
        userRepresentation.setLastName(newUserDto.lastName());
        userRepresentation.setUsername(newUserDto.username());
        userRepresentation.setEmail(newUserDto.email());
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(newUserDto.password());
        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: Status code = " + response.getStatus());
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        log.info("User created with ID: {}", userId);
        return userId;
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }

    @Override
    public void forgotPassword(String username) {
//        UsersResource usersResource = getUsersResource();
//        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
//        UserRepresentation userRepresentation1 = userRepresentations.get(0);
//        UserResource userResource = usersResource.get(userRepresentation1.getId());
//        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);

    }

    @Override
    public List<RoleRepresentation> getUserRoles(String userId) {
        return getUser(userId).roles().realmLevel().listAll();
    }

    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return getUser(userId).groups();
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

}
