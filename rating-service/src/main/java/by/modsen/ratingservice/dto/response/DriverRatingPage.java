package by.modsen.ratingservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DriverRatingPage(
        List<DriverRatingResponse> ratings,
        int currentPage,
        int totalPages,
        long totalElements
) {}