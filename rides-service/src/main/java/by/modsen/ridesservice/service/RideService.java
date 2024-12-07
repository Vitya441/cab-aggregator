package by.modsen.ridesservice.service;

import by.modsen.commonmodule.dto.RideRequest;
import by.modsen.commonmodule.dto.RideResponse;

public interface RideService {

    void requestRide(RideRequest rideRequest);

    RideResponse getCurrentRide(Long passengerId);

    void confirmRide(Long rideId, Long driverId);

    void rejectRide(Long rideId, Long driverId);

}