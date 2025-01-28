package org.example.paymentservice.exception;

public class PaymentException extends BasicException {
    public PaymentException(String message, Object... args) {
        super(message, args);
    }
}