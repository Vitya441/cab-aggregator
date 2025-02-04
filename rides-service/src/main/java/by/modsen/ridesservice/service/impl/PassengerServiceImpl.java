package by.modsen.ridesservice.service.impl;

import by.modsen.ridesservice.client.PassengerClient;
import by.modsen.ridesservice.dto.response.PassengerDto;
import by.modsen.ridesservice.exception.NotFoundException;
import by.modsen.ridesservice.exception.ServiceUnavailableException;
import by.modsen.ridesservice.service.PassengerService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerClient passengerClient;

    @Override
    @CircuitBreaker(name = "passenger", fallbackMethod = "fallBackPassengerService")
    public PassengerDto getPassengerById(Long id) {
        return passengerClient.getPassengerById(id);
    }

    private PassengerDto fallBackPassengerService(Exception ex) {
        log.info("Passenger service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.PASSENGER_SERVICE_UNAVAILABLE);
    }

    private PassengerDto fallBackPassengerService(NotFoundException ex) {
        log.info("Passenger not found. Fallback method was called from rides-service");
        throw new NotFoundException(ex.getMessage());
    }
}