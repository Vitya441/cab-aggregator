package by.modsen.ridesservice.client;

import by.modsen.commonmodule.dto.DriverDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service", url = "http://localhost:8081/api/v1/drivers")
public interface DriverClient {

    @GetMapping("/{id}")
    DriverDto getDriverById(@PathVariable Long id);

}