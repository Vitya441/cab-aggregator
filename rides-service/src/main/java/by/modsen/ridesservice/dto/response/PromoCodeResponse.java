package by.modsen.ridesservice.dto.response;

public record PromoCodeResponse(
        String id,
        String name,
        String code,
        Long count,
        Integer percent
) {}