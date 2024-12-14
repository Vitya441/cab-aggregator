package by.modsen.ratingservice.dto.response;

public record PassengerRatingResponse(
        Long id,
        Long passengerId,
        double rate
) {}