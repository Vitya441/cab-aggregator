package by.modsen.ratingservice.dto.response;

import java.util.List;

public record DriverRatingListResponse(
        List<DriverRatingResponse> ratings
) {}