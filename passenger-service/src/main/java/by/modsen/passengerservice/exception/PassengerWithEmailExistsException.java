package by.modsen.passengerservice.exception;

import lombok.Getter;

@Getter
public class PassengerWithEmailExistsException extends BasicPassengerException {

    public PassengerWithEmailExistsException(String message, Object ... args) {
        super(message, args);
    }
}
