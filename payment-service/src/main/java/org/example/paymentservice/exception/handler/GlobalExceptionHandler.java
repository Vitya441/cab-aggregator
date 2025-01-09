package org.example.paymentservice.exception.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.example.paymentservice.dto.response.ErrorResponse;
import org.example.paymentservice.exception.BalanceException;
import org.example.paymentservice.exception.BasicException;
import org.example.paymentservice.exception.CreateCustomerException;
import org.example.paymentservice.exception.CreatePaymentException;
import org.example.paymentservice.exception.CreateTokenException;
import org.example.paymentservice.exception.NotFoundException;
import org.example.paymentservice.exception.PaymentException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CreateCustomerException.class,
            CreateTokenException.class,
            CreatePaymentException.class,
            PaymentException.class,
            BalanceException.class,

    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(BasicException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = collectErrorMessages(ex);
        ErrorResponse errorDto = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String message = collectErrorMessagesFromViolations(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String collectErrorMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }

    private String collectErrorMessagesFromViolations(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
    }
}