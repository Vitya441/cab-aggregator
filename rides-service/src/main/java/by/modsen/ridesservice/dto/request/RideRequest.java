package by.modsen.ridesservice.dto.request;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.commonmodule.enumeration.PaymentMethod;

public record RideRequest(
        Long passengerId,
        String pickupAddress,
        String destinationAddress,
        CarCategory carCategory,
        PaymentMethod paymentMethod
) {}