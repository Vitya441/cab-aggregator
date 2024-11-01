package by.modsen.passengerservice.exception;

import by.modsen.passengerservice.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(PassengerNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePassengerNotFoundException(PassengerNotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(PassengerWithUsernameExistsException.class)
    public ResponseEntity<ErrorResponseDto> handlePassengerUsernameExistsException(PassengerWithUsernameExistsException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PassengerWithEmailExistsException.class)
    public ResponseEntity<ErrorResponseDto> handlePassengerEmailExistsException(PassengerWithEmailExistsException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PassengerWithPhoneExistsException.class)
    public ResponseEntity<ErrorResponseDto> handlePassengerPhoneExistsException(PassengerWithPhoneExistsException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
