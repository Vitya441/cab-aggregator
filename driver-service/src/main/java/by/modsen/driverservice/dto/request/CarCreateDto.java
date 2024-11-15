package by.modsen.driverservice.dto.request;

public record CarCreateDto(
        String licenseNumber,
        String color,
        int seats,
        String brand,
        String model,
        String category
) {}
