package org.example.paymentservice.exception;

public class CreatePaymentException extends BasicException {
    public CreatePaymentException(String message, Object... args) {
        super(message, args);
    }
}