package by.modsen.authservice.exception;

import by.modsen.authservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<ErrorDto> handleUserCreateException(UserCreateException e) {
        ErrorDto errorDto = new ErrorDto(HttpStatus.CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }
}