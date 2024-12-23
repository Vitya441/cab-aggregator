package by.modsen.driverservice.repository;

import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.entity.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    boolean existsByPhone(String phone);

    List<Driver> findByStatus(DriverStatus status);

    Driver findFirstByStatus(DriverStatus status);

}