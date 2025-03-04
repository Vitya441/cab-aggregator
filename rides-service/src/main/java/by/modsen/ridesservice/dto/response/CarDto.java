package by.modsen.ridesservice.dto.response;

import by.modsen.commonmodule.enumeration.CarCategory;
import lombok.Builder;

@Builder
public record CarDto(
        long id,
        String licenseNumber,
        String color,
        int seats,
        String brand,
        String model,
        CarCategory category
) {}