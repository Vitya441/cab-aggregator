package by.modsen.ridesservice.service;

import by.modsen.ridesservice.dto.request.RideRequest;

import java.math.BigDecimal;

public interface PriceService {
    BigDecimal calculatePriceForRide(RideRequest rideRequest);
}