package by.modsen.priceservice.service;

import by.modsen.priceservice.dto.request.RideRequest;

import java.math.BigDecimal;

public interface PriceCalculationService {

    BigDecimal calculatePrice(RideRequest rideRequest);

}