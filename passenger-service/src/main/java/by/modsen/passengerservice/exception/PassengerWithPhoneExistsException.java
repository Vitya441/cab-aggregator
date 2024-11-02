package by.modsen.passengerservice.exception;

import lombok.Getter;

@Getter
public class PassengerWithPhoneExistsException extends BasicPassengerException {

    public PassengerWithPhoneExistsException(String message, Object ... args) {
        super(message, args);
    }
}
