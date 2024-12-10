package by.modsen.ridesservice.client;

import by.modsen.commonmodule.dto.DriverDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @GetMapping("api/v1/drivers/{id}")
    DriverDto getDriverById(@PathVariable Long id);

}