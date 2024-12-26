package by.modsen.priceservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PaginatedResponse(
        List<TariffResponse> tariffs,
        int currentPage,
        int totalPages,
        long totalElements
) {}