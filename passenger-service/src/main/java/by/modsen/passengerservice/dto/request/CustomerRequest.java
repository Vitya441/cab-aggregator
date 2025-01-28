package by.modsen.passengerservice.dto.request;

import lombok.Builder;

@Builder
public record CustomerRequest(
        String name,
        Long balance
) {}