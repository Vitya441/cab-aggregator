package by.modsen.keycloakservice.dto;

public enum UserRole {
    DRIVER,
    PASSENGER;

    public String getRoleName() {
        return name();
    }
}
