package by.modsen.passengerservice.exception;

public class PassengerWithEmailExistsException extends RuntimeException {
    public PassengerWithEmailExistsException(String message) {
        super(message);
    }
}
