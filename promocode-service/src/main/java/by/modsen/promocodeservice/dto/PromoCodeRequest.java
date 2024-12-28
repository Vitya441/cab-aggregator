package by.modsen.promocodeservice.dto;

public record PromoCodeRequest(
        String name,
        String code,
        Long count,
        Double percent
) {}