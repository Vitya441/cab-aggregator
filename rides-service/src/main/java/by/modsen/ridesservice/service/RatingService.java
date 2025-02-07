package by.modsen.ridesservice.service;

public interface RatingService {

    void updateDriverRating(Long driverId, double rating);

    void updatePassengerRating(Long passengerId, double rating);
}