package by.modsen.passengerservice.exception;

import by.modsen.passengerservice.dto.response.ErrorResponseDto;
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

    @ExceptionHandler({
            PassengerWithUsernameExistsException.class,
            PassengerWithEmailExistsException.class,
            PassengerWithPhoneExistsException.class,
    })
    public ResponseEntity<ErrorResponseDto> handlePassengerExistsException(BasicPassengerException e) {
        String message = getLocalizedMessage(e.getMessage(), e.getArgs());
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.CONFLICT.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePassengerNotFoundException(PassengerNotFoundException e) {
        String message = getLocalizedMessage(e.getMessage(), e.getArgs());
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = collectErrorMessages(ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationExceptions(ConstraintViolationException ex) {
       String message = collectErrorMessagesFromViolations(ex);
       ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), message);
       return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return exceptionMessageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    private String collectErrorMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> getLocalizedMessage(fieldError.getDefaultMessage()))
                .collect(Collectors.joining(", "));
    }

    private String collectErrorMessagesFromViolations(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> getLocalizedMessage(violation.getMessage()))
                .collect(Collectors.joining(", "));
    }
}