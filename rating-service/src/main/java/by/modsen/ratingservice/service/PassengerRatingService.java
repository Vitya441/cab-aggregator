package by.modsen.ratingservice.service;

import by.modsen.ratingservice.dto.response.PassengerRatingListResponse;
import by.modsen.ratingservice.dto.response.PassengerRatingPage;
import by.modsen.ratingservice.dto.response.PassengerRatingResponse;

public interface PassengerRatingService {

    PassengerRatingResponse createRatingRecord(Long passengerId);

    PassengerRatingResponse updateRatingRecord(Long passengerId, double rating);

    PassengerRatingResponse getRatingByPassengerId(Long passengerId);

    PassengerRatingListResponse getAllRatingRecords();

    PassengerRatingPage getRatingsPage(int page, int size, String sortField);

    void deleteRatingRecord(Long passengerId);

}