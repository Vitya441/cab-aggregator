package by.modsen.priceservice.exception;

import by.modsen.commonmodule.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponseDto> handleFeignException(FeignException ex) {
        var errorDetails = objectMapper.readValue(ex.contentUTF8(), Map.class);
        String message = (String) errorDetails.getOrDefault("message", "Unexpected error occurred");
        return ResponseEntity.status(ex.status())
                .body(new ErrorResponseDto(ex.status(), message));
    }

}