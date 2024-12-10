package by.modsen.ridesservice.repository;

import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.entity.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    boolean existsByPassengerIdAndStatusIn(Long passengerId, Collection<RideStatus> statuses);

    Optional<Ride> findByPassengerIdAndStatusIn(Long passengerId, Collection<RideStatus> statuses);

    Page<Ride> findAllByPassengerIdAndStatus(Long passengerId, RideStatus status, Pageable pageable);

    Page<Ride> findAllByDriverIdAndStatus(Long driverId, RideStatus status, Pageable pageable);

    Optional<Ride> findByPassengerIdAndStatusNot(Long passengerId, RideStatus status);

    boolean existsByPassengerIdAndStatusNot(Long passengerId, RideStatus status);

}