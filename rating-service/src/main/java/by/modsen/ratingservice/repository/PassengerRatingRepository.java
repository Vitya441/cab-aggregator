package by.modsen.ratingservice.repository;

import by.modsen.ratingservice.entity.PassengerRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRatingRepository extends JpaRepository<PassengerRating, Long> {

    Optional<PassengerRating> findByPassengerId(Long passengerId);

    boolean existsByPassengerId(Long passengerId);
}