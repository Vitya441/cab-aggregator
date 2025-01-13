package by.modsen.driverservice.dto.response;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String customerId,
        String name,
        Long balance
) {}