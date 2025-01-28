package by.modsen.driverservice.service;

import by.modsen.commonmodule.enumeration.DriverStatus;
import by.modsen.driverservice.dto.response.DriverDto;

public interface AvailableDriverService {

    DriverDto getAvailableDriver(Long rideId);

    void changeDriverStatus(Long driverId, DriverStatus driverStatus);

    void updateLastRejectedRide(Long driverId, Long rideId);
}