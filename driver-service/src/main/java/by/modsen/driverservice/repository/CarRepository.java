package by.modsen.driverservice.repository;

import by.modsen.driverservice.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findCarByDriverIsNull(Pageable pageable);

    boolean existsByLicenseNumber(String licenseNumber);

}