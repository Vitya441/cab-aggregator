package by.modsen.promocodeservice.dto;

public record PromoCodeResponse(
        String id,
        String name,
        String code,
        Long count,
        Integer percent
) {}