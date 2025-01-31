package by.modsen.driverservice.dto.response;

import by.modsen.driverservice.entity.enums.CarCategory;
import lombok.Builder;

@Builder
public record CarDto(
        long id,
        String licenseNumber,
        String color,
        int seats,
        String brand,
        String model,
//        TODO: String -> CarCategory
        CarCategory category
) {}