package by.modsen.ridesservice.util;

import by.modsen.commonmodule.dto.DriverDto;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.CantPerformOperationException;
import org.springframework.stereotype.Component;

@Component
public class RideValidator {

    public void validateConfirmation(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.CANCELLED) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_CONFIRMED);
        }
    }

    public void validateRejection(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.CANCELLED) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_REJECTED);
        }
    }

    public void validateStarting(DriverDto driverDto, Ride ride) {
        if (!driverDto.id().equals(ride.getDriverId()) || ride.getStatus() != RideStatus.ACCEPTED) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_STARTED);
        }
    }

    public void validateEnding(DriverDto driverDto, Ride ride) {
        if (!driverDto.id().equals(ride.getDriverId()) || ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new CantPerformOperationException(ExceptionMessageConstants.RIDE_CANNOT_BE_COMPLETED);
        }
    }
}