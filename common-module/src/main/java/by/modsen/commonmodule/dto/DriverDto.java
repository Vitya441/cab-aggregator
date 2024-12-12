package by.modsen.commonmodule.dto;

public record DriverDto(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String status
) {}