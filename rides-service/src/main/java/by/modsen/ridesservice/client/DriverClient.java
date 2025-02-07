package by.modsen.ridesservice.client;

import by.modsen.ridesservice.config.FeignClientConfig;
import by.modsen.ridesservice.dto.response.DriverDto;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "${app.feign-clients.driver-service.url}",
        contextId = "driverClient",
        configuration = FeignClientConfig.class
)
public interface DriverClient {

    @GetMapping("api/v1/drivers/{id}")
    DriverDto getDriverById(@PathVariable Long id);

    @GetMapping("api/v1/drivers/with-car/{id}")
    DriverWithCarDto getByIdWithCar(@PathVariable Long id);
}