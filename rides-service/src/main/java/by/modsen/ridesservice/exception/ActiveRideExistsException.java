package by.modsen.ridesservice.exception;

public class ActiveRideExistsException extends RuntimeException {
    public ActiveRideExistsException(String message) {
        super(message);
    }
}
