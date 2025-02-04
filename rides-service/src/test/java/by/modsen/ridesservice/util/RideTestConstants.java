package by.modsen.ridesservice.util;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.commonmodule.enumeration.PaymentMethod;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.dto.request.Point;
import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.CarDto;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import by.modsen.ridesservice.dto.response.PassengerDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.entity.Ride;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class RideTestConstants {

    public static final Long RIDE_ID = 1L;
    public static final Long RIDE_ID_NEXT = 2L;
    public static final Long PASSENGER_ID = 1L;
    public static final Long DRIVER_ID = 1L;
    public static final Long CAR_ID = 1L;
    public static final String PICKUP_ADDRESS = "123 Main St";
    public static final String PICKUP_ADDRESS_NEXT = "151 Another St";
    public static final String DESTINATION_ADDRESS = "456 Elm St";
    public static final CarCategory CAR_CATEGORY = CarCategory.COMFORT;
    public static final BigDecimal ESTIMATED_COST = BigDecimal.valueOf(25.50);
    public static final RideStatus STATUS = RideStatus.REQUESTED;
    public static final RideStatus ACCEPTED_STATUS = RideStatus.ACCEPTED;
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.CREDIT_CARD;
    public static final LocalDateTime START_TIME = LocalDateTime.of(2024, 2, 1, 10, 30);
    public static final LocalDateTime END_TIME = LocalDateTime.of(2024, 2, 1, 11, 0);
    public static final String PASSENGER_NAME = "John";
    public static final String CAR_LICENCE_NUMBER = "ABC123";
    public static final String CAR_COLOR = "Red";

    public static final String RIDE_PATH = "/api/v1/rides";
    public static final String DRIVER_PATH = "/api/v1/drivers";
    public static final String PASSENGER_PATH = "/api/v1/passengers";
    public static final String PAYMENT_CUSTOMERS_PATH = "/api/v1/payments/customers/charge";
    public static final String PRICE_PATH = "/api/v1/price/calculate";
    public static final String RATING_PASSENGERS_PATH = "/api/v1/ratings/passengers";
    public static final String RATING_DRIVERS_PATH = "/api/v1/ratings/drivers";

    public static RideRequest getRideRequest() {
        return RideRequest.builder()
                .passengerId(PASSENGER_ID)
                .pickupAddress(getPickupPoint())
                .destinationAddress(getDestinationPoint())
                .carCategory(CAR_CATEGORY)
                .paymentMethod(PAYMENT_METHOD)
                .build();
    }

    public static Ride getRideWithoutId() {
        return Ride.builder()
                .passengerId(PASSENGER_ID)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .paymentMethod(PAYMENT_METHOD)
                .estimatedCost(ESTIMATED_COST)
                .build();
    }

    public static Ride getRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .paymentMethod(PAYMENT_METHOD)
                .estimatedCost(ESTIMATED_COST)
                .build();
    }

    public static Ride getThirdRideDb() {
        return Ride.builder()
                .id(3L)
                .passengerId(103L)
                .driverId(204L)
                .pickupAddress("456 Maple St")
                .destinationAddress("202 Birch St")
                .estimatedCost(BigDecimal.valueOf(20))
                .status(RideStatus.ACCEPTED)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .build();
    }

    public static Ride getSecondRide() {
        return Ride.builder()
                .id(RIDE_ID_NEXT)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .pickupAddress(PICKUP_ADDRESS_NEXT)
                .destinationAddress(DESTINATION_ADDRESS)
                .paymentMethod(PAYMENT_METHOD)
                .estimatedCost(ESTIMATED_COST)
                .build();
    }

    public static Ride getAcceptedRide() {
        return Ride.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .status(ACCEPTED_STATUS)
                .paymentMethod(PAYMENT_METHOD)
                .estimatedCost(ESTIMATED_COST)
                .build();
    }

    public static Ride getRideWithDriver() {
        return Ride.builder()
                .id(RIDE_ID)
                .driverId(DRIVER_ID)
                .passengerId(PASSENGER_ID)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .paymentMethod(PAYMENT_METHOD)
                .estimatedCost(ESTIMATED_COST)
                .build();
    }

    public static PassengerDto getPassengerDto() {
        return PassengerDto.builder()
                .id(PASSENGER_ID)
                .firstName(PASSENGER_NAME)
                .build();
    }

    public static DriverWithCarDto getDriverWithCarDto() {
        return DriverWithCarDto.builder()
                .id(DRIVER_ID)
                .car(getCarDto())
                .build();
    }

    public static RideResponse getRideResponse() {
        return RideResponse.builder()
                .id(RIDE_ID)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .pickupAddress(PICKUP_ADDRESS)
                .destinationAddress(DESTINATION_ADDRESS)
                .estimatedCost(ESTIMATED_COST)
                .paymentMethod(PAYMENT_METHOD)
                .build();
    }

    public static RideResponse getSecondRideResponse() {
        return RideResponse.builder()
                .id(RIDE_ID_NEXT)
                .passengerId(PASSENGER_ID)
                .driverId(DRIVER_ID)
                .pickupAddress(PICKUP_ADDRESS_NEXT)
                .destinationAddress(DESTINATION_ADDRESS)
                .estimatedCost(ESTIMATED_COST)
                .paymentMethod(PAYMENT_METHOD)
                .build();
    }

    public static Point getPickupPoint() {
        return  new Point(
                1.0,
                1.0,
                PICKUP_ADDRESS
        );
    }

    public static Point getDestinationPoint() {
        return  new Point(
                1.0,
                1.0,
                DESTINATION_ADDRESS
        );
    }

    public static CarDto getCarDto() {
        return CarDto.builder()
                .id(CAR_ID)
                .licenseNumber(CAR_LICENCE_NUMBER)
                .color(CAR_COLOR)
                .build();
    }


    private RideTestConstants() {}
}