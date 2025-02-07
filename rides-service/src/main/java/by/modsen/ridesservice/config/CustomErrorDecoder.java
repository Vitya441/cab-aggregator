package by.modsen.ridesservice.config;

import by.modsen.ridesservice.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.resolve(response.status());
        String errorMessage = "Unknown error occurred";

        try {
            if (response.body() != null) {
                String body = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                JsonNode jsonNode = objectMapper.readTree(body);
                if (jsonNode.has("message")) {
                    errorMessage = jsonNode.get("message").asText();
                }
            }
        } catch (IOException e) {
            log.error("Error decoding Feign response: {}", e.getMessage());
        }
        if (status == HttpStatus.NOT_FOUND) {
            return new NotFoundException(errorMessage);
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}