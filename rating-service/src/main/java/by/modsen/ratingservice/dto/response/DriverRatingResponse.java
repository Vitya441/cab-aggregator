package by.modsen.ratingservice.dto.response;

public record DriverRatingResponse(
        Long id,
        Long driverId,
        double rate
) {}