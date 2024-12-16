package by.modsen.ratingservice.service;

import by.modsen.ratingservice.dto.response.DriverRatingListResponse;
import by.modsen.ratingservice.dto.response.DriverRatingPage;
import by.modsen.ratingservice.dto.response.DriverRatingResponse;

public interface DriverRatingService {

    DriverRatingResponse createRatingRecord(Long driverId);

    DriverRatingResponse updateRatingRecord(Long driverId, double rating);

    DriverRatingResponse getRatingByDriverId(Long driverId);

    DriverRatingListResponse getAllRatingRecords();

    DriverRatingPage getRatingsPage(int page, int size, String sortField);

    void deleteRatingRecord(Long driverId);

}