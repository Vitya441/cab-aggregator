package by.modsen.ridesservice.client;

import by.modsen.ridesservice.config.FeignClientConfig;
import by.modsen.ridesservice.dto.response.PassengerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "${app.feign-clients.passenger-service.url}",
        contextId = "passengerClient",
        configuration = FeignClientConfig.class
)
public interface PassengerClient {

    @GetMapping("/api/v1/passengers/{id}")
    PassengerDto getPassengerById(@PathVariable Long id);
}