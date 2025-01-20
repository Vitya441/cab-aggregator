package by.modsen.authservice.service.impl;

import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.exception.UserCreateException;
import by.modsen.authservice.service.UserService;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final String realm;

    private final Keycloak keycloak;

    public UserServiceImpl(@Value("${app.keycloak.realm}") String realm, Keycloak keycloak) {
        this.realm = realm;
        this.keycloak = keycloak;
    }

    @Override
    public String createUser(NewUserDto newUserDto) {
        UserRepresentation userRepresentation = createUserRepresentation(newUserDto);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        if (response.getStatus() != HttpStatus.CREATED.value()) {
            throw new UserCreateException("User with this credentials already exists");
        }
        String userId = extractUserIdFromLocation(response.getLocation());
        log.info("User created with ID: {}", userId);

        return userId;
    }

    @Override
    public boolean isUserDataTaken(String email, String username) {
        UsersResource usersResource = getUsersResource();

        List<UserRepresentation> emailUsers = usersResource.searchByEmail(email, true);
        if (!emailUsers.isEmpty()) {
            log.error("User with email {} already exists", email);
            return true;
        }

        List<UserRepresentation> usernameUsers = usersResource.search(username, true);
        if (!usernameUsers.isEmpty()) {
            log.error("User with username {} already exists", username);
            return true;
        }

        return false;
    }

    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
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

    private UserRepresentation createUserRepresentation(NewUserDto newUserDto) {
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

        return userRepresentation;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private String extractUserIdFromLocation(URI location) {
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}