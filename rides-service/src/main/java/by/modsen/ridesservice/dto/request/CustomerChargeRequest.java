package by.modsen.ridesservice.dto.request;

import lombok.Builder;

@Builder
public record CustomerChargeRequest(
        String customerId,
        String currency,
        Long amount
) {}