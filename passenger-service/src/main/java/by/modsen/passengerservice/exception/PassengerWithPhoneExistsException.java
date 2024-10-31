package by.modsen.passengerservice.exception;

public class PassengerWithPhoneExistsException extends RuntimeException {
    public PassengerWithPhoneExistsException(String message) {
        super(message);
    }
}
