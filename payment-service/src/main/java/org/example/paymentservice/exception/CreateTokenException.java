package org.example.paymentservice.exception;

public class CreateTokenException extends BasicException {
    public CreateTokenException(String message, Object... args) {
        super(message, args);
    }
}