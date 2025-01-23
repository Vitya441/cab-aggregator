package by.modsen.priceservice.dto.request;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.commonmodule.enumeration.PaymentMethod;

public record RideRequest(
        Long passengerId,
        Point pickupAddress,
        Point destinationAddress,
        CarCategory carCategory,
        PaymentMethod paymentMethod,
        String promocode
) {}