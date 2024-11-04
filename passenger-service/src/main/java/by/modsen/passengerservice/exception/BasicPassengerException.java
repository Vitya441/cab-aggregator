package by.modsen.passengerservice.exception;

import lombok.Getter;

@Getter
public class BasicPassengerException extends RuntimeException {

    private final Object[] args;

    public BasicPassengerException(String message, Object ... args) {
        super(message);
        this.args = args;
    }
}