package by.modsen.ridesservice.dto.response;

import lombok.Builder;

@Builder
public record DriverDto(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String status
) {}