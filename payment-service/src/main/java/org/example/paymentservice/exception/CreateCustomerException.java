package org.example.paymentservice.exception;

public class CreateCustomerException extends BasicException {
    public CreateCustomerException(String message, Object... args) {
        super(message, args);
    }
}