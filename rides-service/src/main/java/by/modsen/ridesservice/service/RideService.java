package by.modsen.ridesservice.service;

import by.modsen.commonmodule.dto.RideRequest;
import by.modsen.commonmodule.dto.RideResponse;
import by.modsen.ridesservice.dto.PaginationDto;

public interface RideService {

    void requestRide(RideRequest rideRequest);

    void assignDriverToRide(Long driverId, Long rideId);

    RideResponse getCurrentRide(Long passengerId);

    PaginationDto<RideResponse> getHistoryByPassengerId(Long passengerId, int page, int size, String sortField);

    PaginationDto<RideResponse> getHistoryByDriverId(Long passengerId, int page, int size, String sortField);

    void confirmRide(Long rideId, Long driverId);

    void rejectRide(Long rideId, Long driverId);

    void startRide(Long rideId, Long driverId);

    void endRide(Long rideId, Long driverId);
}