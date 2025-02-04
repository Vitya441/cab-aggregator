package by.modsen.ridesservice.service.impl;

import by.modsen.ridesservice.client.PriceClient;
import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.exception.ServiceUnavailableException;
import by.modsen.ridesservice.service.PriceService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceClient priceClient;

    @Override
    @CircuitBreaker(name = "price", fallbackMethod = "fallBackPriceService")
    public BigDecimal calculatePriceForRide(RideRequest rideRequest) {
        return priceClient.calculatePriceForRide(rideRequest);
    }

    private BigDecimal fallBackPriceService(Exception ex) {
        log.info("Price service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.PRICE_SERVICE_UNAVAILABLE);
    }
}