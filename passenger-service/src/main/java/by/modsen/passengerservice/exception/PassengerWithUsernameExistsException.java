package by.modsen.passengerservice.exception;

public class PassengerWithUsernameExistsException extends RuntimeException {
    public PassengerWithUsernameExistsException(String message) {
        super(message);
    }
}
