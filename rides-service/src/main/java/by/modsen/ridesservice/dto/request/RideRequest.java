package by.modsen.ridesservice.dto.request;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.commonmodule.enumeration.PaymentMethod;
import lombok.Builder;

@Builder
public record RideRequest(
        Long passengerId,
        Point pickupAddress,
        Point destinationAddress,
        CarCategory carCategory,
        PaymentMethod paymentMethod,
        String promocode
) {}