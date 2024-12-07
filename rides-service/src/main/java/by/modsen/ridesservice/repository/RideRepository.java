package by.modsen.ridesservice.repository;

import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    Optional<Ride> findByPassengerIdAndStatusNot(Long passengerId, RideStatus excludedStatus);

    boolean existsByPassengerIdAndStatusNot(Long passengerId, RideStatus excludedStatus);

    Optional<Ride> findByIdAndStatusIs(Long id, RideStatus status);

}