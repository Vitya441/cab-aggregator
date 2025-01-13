package org.example.paymentservice.exception;

public class NotFoundException extends BasicException {
    public NotFoundException(String message, Object... args) {
        super(message, args);
    }
}