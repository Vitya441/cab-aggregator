package by.modsen.ridesservice.dto.response;

import lombok.Builder;

@Builder
public record ChargeResponse(
        String id,
        String currency,
        long amount
) {}