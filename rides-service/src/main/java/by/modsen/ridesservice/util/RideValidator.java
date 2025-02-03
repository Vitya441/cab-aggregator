package by.modsen.ridesservice.util;

import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.CantPerformOperationException;
import org.springframework.stereotype.Component;

@Component
public class RideValidator {

    public void validateConfirmation(Ride ride, Long driverId) {
        if (ride.getStatus() != RideStatus.REQUESTED || !ride.getDriverId().equals(driverId)) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_CONFIRMED);
        }
    }

    public void validateRejection(Ride ride, Long driverId) {
        if (ride.getStatus() != RideStatus.REQUESTED || !ride.getDriverId().equals(driverId)) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_REJECTED);
        }
    }

    public void validateStarting(Ride ride, Long driverId) {
        if (ride.getStatus() != RideStatus.ACCEPTED || !ride.getDriverId().equals(driverId)) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_STARTED);
        }
    }

    public void validateEnding(Ride ride, Long driverId) {
        if (ride.getStatus() != RideStatus.IN_PROGRESS || !ride.getDriverId().equals(driverId) ) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_COMPLETED);
        }
    }

    public void validateDriverRating(Ride ride, Long passengerId) {
        if (ride.getStatus() != RideStatus.COMPLETED || !ride.getPassengerId().equals(passengerId) || ride.isRatedByPassenger()) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_RATE);
        }
    }

    public void validatePassengerRating(Ride ride, Long driverId) {
        if (ride.getStatus() != RideStatus.COMPLETED || !ride.getDriverId().equals(driverId) || ride.isRatedByDriver()) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_RATE);
        }
    }
}