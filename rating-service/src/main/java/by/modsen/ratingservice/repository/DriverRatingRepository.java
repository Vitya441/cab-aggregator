package by.modsen.ratingservice.repository;

import by.modsen.ratingservice.entity.DriverRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRatingRepository extends JpaRepository<DriverRating, Long> {

    Optional<DriverRating> findByDriverId(Long driverId);

    boolean existsByDriverId(Long driverId);
}