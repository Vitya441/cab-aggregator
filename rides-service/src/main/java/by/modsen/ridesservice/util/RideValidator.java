package by.modsen.ridesservice.util;

import by.modsen.commonmodule.dto.DriverDto;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.NotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RideValidator {

    public void validateConfirmation(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.CANCELLED) {
            throw new NotFoundException("Ride is not waiting for a driver");
        }
    }

    public void validateRejection(Ride ride) {
        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.CANCELLED) {
            throw new NotFoundException("You cant reject this ride");
        }
    }

    public void validateStarting(DriverDto driverDto, Ride ride) {
        if (!driverDto.id().equals(ride.getDriverId()) || ride.getStatus() != RideStatus.ACCEPTED) {
            throw new NotFoundException("You cant start this ride");
        }
    }

    public void validateEnding(DriverDto driverDto, Ride ride) {
        if (!driverDto.id().equals(ride.getDriverId()) || ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new NotFoundException("You cant complete this ride");
        }
    }
}