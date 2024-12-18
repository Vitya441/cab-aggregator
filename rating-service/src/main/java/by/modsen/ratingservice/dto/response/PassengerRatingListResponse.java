package by.modsen.ratingservice.dto.response;

import java.util.List;

public record PassengerRatingListResponse(
        List<PassengerRatingResponse> ratings
) {}