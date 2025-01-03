package by.modsen.promocodeservice.exception;

import by.modsen.promocodeservice.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource exceptionMessageSource;

    @ExceptionHandler(PromoCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(PromoCodeNotFoundException ex) {
        String message = getLocalizedMessage(ex.getMessage(), ex.getArgs());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CodeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistsException(CodeAlreadyExistsException ex) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        String message = getArgumentErrorMessages(ex);
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

    private String getArgumentErrorMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> getLocalizedMessage(fieldError.getDefaultMessage()))
                .collect(Collectors.joining(", "));
    }
}