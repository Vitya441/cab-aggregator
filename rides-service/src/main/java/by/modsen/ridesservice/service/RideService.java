package by.modsen.ridesservice.service;

import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.PaginationDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;

public interface RideService {

    void requestRide(RideRequest rideRequest);

    void assignDriverToRide(Long driverId, Long rideId);

    RideWithDriverResponse getActiveRide(Long passengerId);

    PaginationDto<RideResponse> getHistoryByPassengerId(Long passengerId, int page, int size, String sortField);

    PaginationDto<RideResponse> getHistoryByDriverId(Long passengerId, int page, int size, String sortField);

    void confirmRide(Long rideId, Long driverId);

    void rejectRide(Long rideId, Long driverId);

    void startRide(Long rideId, Long driverId);

    void endRide(Long rideId, Long driverId);

    void rateDriver(Long rideId, double rating);

    void ratePassenger(Long rideId, double rating);
}