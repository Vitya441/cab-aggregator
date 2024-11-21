package by.modsen.driverservice.exception;

import by.modsen.driverservice.dto.response.ErrorDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource exceptionMessageSource;

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex) {
        String message = getLocalizedMessage(ex.getMessage(), ex.getArgs());
        ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND.value(), message);

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            PhoneExistsException.class,
            LicenseNumberExistsException.class,
            CarAlreadyAssignedException.class
    })
    public ResponseEntity<ErrorDto> handleConflictExceptions(BasicException ex) {
        String message = getLocalizedMessage(ex.getMessage(), ex.getArgs());
        ErrorDto errorDto = new ErrorDto(HttpStatus.CONFLICT.value(), message);

        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = collectErrorMessages(ex);
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message);

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        String message = collectErrorMessagesFromViolations(ex);
        ErrorDto errorResponse = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Валидация Enum
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleValidationException(HttpMessageNotReadableException exception) {
        String errorDetails = "";
        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
            if (ifx.getTargetType()!=null && ifx.getTargetType().isEnum()) {
                errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }
        ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorDetails);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
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