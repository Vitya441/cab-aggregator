package by.modsen.commonmodule.dto;

import lombok.Builder;

@Builder
public record CustomerEvent(
        String name,
        Long balance
) {}