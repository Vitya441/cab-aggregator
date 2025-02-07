package by.modsen.ridesservice.service.impl;

import by.modsen.ridesservice.client.RatingClient;
import by.modsen.ridesservice.exception.ServiceUnavailableException;
import by.modsen.ridesservice.service.RatingService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingClient ratingClient;

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallBackDriverRating")
    public void updateDriverRating(Long driverId, double rating) {
        ratingClient.updateDriverRating(driverId, rating);
    }

    @Override
    @CircuitBreaker(name = "rating", fallbackMethod = "fallBackPassengerRating")
    public void updatePassengerRating(Long passengerId, double rating) {
        ratingClient.updatePassengerRating(passengerId, rating);
    }

    private void fallBackDriverRating(Exception ex) {
        log.info("Rating service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.RATING_SERVICE_UNAVAILABLE);
    }

    private void fallBackPassengerRating(Exception ex) {
        log.info("Rating service is not available. Fallback method was called from rides-service");
        throw new ServiceUnavailableException(ExceptionMessageConstants.RATING_SERVICE_UNAVAILABLE);
    }
}