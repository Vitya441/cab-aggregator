package org.example.paymentservice.exception;

import lombok.Getter;

@Getter
public abstract class BasicException extends RuntimeException {

    private final Object[] args;

    public BasicException(String message, Object... args) {
        super(message);
        this.args = args;
    }
}