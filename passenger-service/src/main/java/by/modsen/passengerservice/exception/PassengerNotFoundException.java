package by.modsen.passengerservice.exception;

import lombok.Getter;

@Getter
public class PassengerNotFoundException extends BasicPassengerException {

    public PassengerNotFoundException(String message, Object... args) {
        super(message, args);
    }
}