package by.modsen.ratingservice.dto.response;

public record ErrorResponse(
        int statusCode,
        String message
) {}