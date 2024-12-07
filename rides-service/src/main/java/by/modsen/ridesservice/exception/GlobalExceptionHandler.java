package by.modsen.ridesservice.exception;

import by.modsen.commonmodule.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Обрабатываю Feign-исключение, чтобы вытянуть нужное сообщение, которое бросил вызванный микросервис
     */
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
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.CONFLICT.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleActiveRideNotFoundException(NotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }
}