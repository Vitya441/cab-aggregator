package by.modsen.authservice.service;

public interface RoleService {

    void assignRole(String userId, String roleName);

    void deleteFromUser(String userId, String roleName);
}