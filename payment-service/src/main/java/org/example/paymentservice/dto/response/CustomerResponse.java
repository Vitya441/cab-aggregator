package org.example.paymentservice.dto.response;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String customerId,
        String name,
        Long balance
) {}