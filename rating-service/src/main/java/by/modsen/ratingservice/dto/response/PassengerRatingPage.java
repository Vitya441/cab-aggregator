package by.modsen.ratingservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PassengerRatingPage(
        List<PassengerRatingResponse> ratings,
        int currentPage,
        int totalPages,
        long totalElements
) {}