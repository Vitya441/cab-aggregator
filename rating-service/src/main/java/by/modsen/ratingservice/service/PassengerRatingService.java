package by.modsen.ratingservice.service;

import by.modsen.ratingservice.dto.response.PassengerRatingResponse;
import java.util.List;

public interface PassengerRatingService {

    PassengerRatingResponse createRatingRecord(Long passengerId);

    PassengerRatingResponse updateRatingRecord(Long passengerId, double rating);

    PassengerRatingResponse getRatingByPassengerId(Long passengerId);

    List<PassengerRatingResponse> getAllRatingRecords();

    void deleteRatingRecord(Long passengerId);

}