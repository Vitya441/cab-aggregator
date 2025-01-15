package by.modsen.driverservice.repository;

import by.modsen.commonmodule.enumeration.DriverStatus;
import by.modsen.driverservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    boolean existsByPhone(String phone);

    Optional<Driver> findFirstByStatusAndRejectedRideIdNotOrRejectedRideIdIsNull(DriverStatus status, Long rideId);
}