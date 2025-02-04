package by.modsen.ridesservice.service.impl;

import by.modsen.ridesservice.client.DriverClient;
import by.modsen.ridesservice.dto.response.DriverDto;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import by.modsen.ridesservice.exception.NotFoundException;
import by.modsen.ridesservice.exception.ServiceUnavailableException;
import by.modsen.ridesservice.service.DriverService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverClient driverClient;

    @Override
    @CircuitBreaker(name = "driver", fallbackMethod = "fallBackDriver")
    public DriverDto getDriverById(Long id) {
        return driverClient.getDriverById(id);
    }

    @Override
    @CircuitBreaker(name = "driver", fallbackMethod = "fallBackDriverWithCar")
    public DriverWithCarDto getByIdWithCar(Long id) {
        return driverClient.getByIdWithCar(id);
    }

    private DriverDto fallBackDriver(Exception ex) {
        log.info("Driver service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.DRIVER_SERVICE_UNAVAILABLE);
    }

    private DriverWithCarDto fallBackDriverWithCar(Exception ex) {
        log.info("Driver service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.DRIVER_SERVICE_UNAVAILABLE);
    }

    private DriverDto fallBackDriver(NotFoundException ex) {
        log.info("Driver not found. Fallback method was called from rides-service");
        throw new NotFoundException(ex.getMessage());
    }

    private DriverWithCarDto fallBackDriverWithCar(NotFoundException ex) {
        log.info("Driver not found. Fallback method was called from rides-service");
        throw new NotFoundException(ex.getMessage());
    }
}