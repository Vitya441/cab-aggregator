package by.modsen.ridesservice.exception;

import by.modsen.commonmodule.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageSource exceptionMessageSource;

    @SneakyThrows
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseDto> handleFeignException(FeignException ex) {
            var errorDetails = objectMapper.readValue(ex.contentUTF8(), Map.class);
            String message = (String) errorDetails.getOrDefault("message", "Unexpected error occurred");
            return ResponseEntity.status(ex.status())
                    .body(new ErrorResponseDto(ex.status(), message));
    }

    @ExceptionHandler(ActiveRideExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleActiveRideExistsException(ActiveRideExistsException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.CONFLICT.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(CantPerformOperationException.class)
    public ResponseEntity<ErrorResponseDto> handleCantPerformOperationException(CantPerformOperationException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.CONFLICT.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }



    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleActiveRideNotFoundException(NotFoundException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return exceptionMessageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }



}