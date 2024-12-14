package by.modsen.ratingservice.service;

import by.modsen.ratingservice.dto.response.DriverRatingResponse;

import java.util.List;

public interface DriverRatingService {

    DriverRatingResponse createRatingRecord(Long driverId);

    DriverRatingResponse updateRatingRecord(Long driverId, double rating);

    DriverRatingResponse getRatingByDriverId(Long driverId);

    List<DriverRatingResponse> getAllRatingRecords();

    void deleteRatingRecord(Long driverId);

}