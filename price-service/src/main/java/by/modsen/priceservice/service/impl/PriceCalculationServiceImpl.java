package by.modsen.priceservice.service.impl;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.priceservice.dto.request.Point;
import by.modsen.priceservice.dto.request.RideRequest;
import by.modsen.priceservice.entity.Tariff;
import by.modsen.priceservice.service.PriceCalculationService;
import by.modsen.priceservice.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final TariffService tariffService;

    @Override
    public BigDecimal calculatePrice(RideRequest rideRequest) {
        CarCategory carCategory = rideRequest.carCategory();
        Tariff tariff = tariffService.getByCarCategory(carCategory);

        Point pickup = rideRequest.pickupAddress();
        Point destination = rideRequest.destinationAddress();
        double distance = calculateDistanceInKm(pickup, destination);
        BigDecimal distanceInKm = BigDecimal.valueOf(distance);
        BigDecimal price = tariff.getCostPerKm().multiply(distanceInKm);

        return price;
    }

    private double calculateDistanceInKm(Point pickup, Point destination) {
        return Math.sqrt(Math.pow(destination.x() - pickup.x(), 2) + Math.pow(destination.y() - pickup.y(), 2));
    }

}
