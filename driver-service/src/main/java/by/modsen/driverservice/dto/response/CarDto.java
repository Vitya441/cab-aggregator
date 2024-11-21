package by.modsen.driverservice.dto.response;

public record CarDto(
        long id,
        String licenseNumber,
        String color,
        int seats,
        String brand,
        String model,
        String category
) {}