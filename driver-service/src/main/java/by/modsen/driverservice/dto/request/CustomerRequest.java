package by.modsen.driverservice.dto.request;

import lombok.Builder;

@Builder
public record CustomerRequest(
        String name,
        Long balance
) {}