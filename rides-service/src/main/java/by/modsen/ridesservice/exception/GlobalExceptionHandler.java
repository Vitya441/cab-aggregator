package by.modsen.ridesservice.exception;

import by.modsen.ridesservice.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageSource exceptionMessageSource;

//    @SneakyThrows
//    @ExceptionHandler(FeignException.class)
//    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
//            var errorDetails = objectMapper.readValue(ex.contentUTF8(), Map.class);
//            String message = (String) errorDetails.getOrDefault("message", "Unexpected error occurred");
//            return ResponseEntity.status(ex.status())
//                    .body(new ErrorResponse(ex.status(), message));
//    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleServiceUnavailableException(ServiceUnavailableException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), localizedMessage);

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ActiveRideExistsException.class)
    public ResponseEntity<ErrorResponse> handleActiveRideExistsException(ActiveRideExistsException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(CantPerformOperationException.class)
    public ResponseEntity<ErrorResponse> handleCantPerformOperationException(CantPerformOperationException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponse ErrorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse);
    }

    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleActiveRideNotFoundException(RideNotFoundException ex) {
        String localizedMessage = getLocalizedMessage(ex.getMessage());
        ErrorResponse ErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), localizedMessage);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse);
    }

    // Для CustomerErrorDecoder FeignClient
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse ErrorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse);
    }

    private String getLocalizedMessage(String key, Object... args) {
        return exceptionMessageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

}