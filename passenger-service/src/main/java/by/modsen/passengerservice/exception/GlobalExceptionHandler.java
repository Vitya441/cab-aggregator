package by.modsen.passengerservice.exception;

import by.modsen.passengerservice.dto.ErrorResponseDto;
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
    private final MessageSource validationMessageSource;

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
        String errorMessage = collectErrorMessages(ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return exceptionMessageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    private String collectErrorMessages(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> validationMessageSource.getMessage(
                        fieldError.getDefaultMessage(), null, LocaleContextHolder.getLocale()))
                .collect(Collectors.joining(", "));
    }
}
