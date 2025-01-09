package org.example.paymentservice.dto.response;

import lombok.Builder;

@Builder
public record ChargeResponse(
        String id,
        String currency,
        long amount
) {}