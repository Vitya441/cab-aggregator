package by.modsen.ratingservice.exception;

public class RatingAlreadyExistsException extends BasicException {
    public RatingAlreadyExistsException(String message, Object... args) {
        super(message, args);
    }
}
