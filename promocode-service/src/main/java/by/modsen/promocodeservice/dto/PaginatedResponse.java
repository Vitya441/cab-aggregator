package by.modsen.promocodeservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginatedResponse(
        List<PromoCodeResponse> promoCodes,
        int currentPage,
        int totalPages,
        long totalElements
) {}