package by.modsen.ridesservice.client;

import by.modsen.ridesservice.dto.request.RideRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "price-service")
public interface PriceClient {

    @PostMapping("/api/v1/price/calculate")
    BigDecimal calculatePriceForRide(@RequestBody RideRequest rideRequest);
}
