package by.modsen.ratingservice.exception;

public class RatingNotFoundException extends BasicException {
    public RatingNotFoundException(String message, Object... args) {
        super(message, args);
    }
}