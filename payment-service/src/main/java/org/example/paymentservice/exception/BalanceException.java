package org.example.paymentservice.exception;

public class BalanceException extends BasicException {
    public BalanceException(String message, Object... args) {
        super(message, args);
    }
}