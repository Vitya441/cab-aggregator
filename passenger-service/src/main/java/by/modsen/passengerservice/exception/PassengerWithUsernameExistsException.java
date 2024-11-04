package by.modsen.passengerservice.exception;

import lombok.Getter;

@Getter
public class PassengerWithUsernameExistsException extends BasicPassengerException {

    public PassengerWithUsernameExistsException(String message, Object ... args) {
        super(message, args);
    }
}