package by.modsen.ratingservice.exception;

import by.modsen.ratingservice.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource exceptionMessageSource;

    @ExceptionHandler(RatingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(RatingNotFoundException ex) {
        String message = getLocalizedMessage(ex.getMessage(), ex.getArgs());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RatingAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(RatingAlreadyExistsException ex) {
        String message = getLocalizedMessage(ex.getMessage(), ex.getArgs());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = getConstraintsErrorMessage(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return exceptionMessageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    private String getConstraintsErrorMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> getLocalizedMessage(violation.getMessage()))
                .collect(Collectors.joining(", "));
    }
}