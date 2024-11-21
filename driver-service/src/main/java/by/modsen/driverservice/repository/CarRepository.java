package by.modsen.driverservice.repository;

import by.modsen.driverservice.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c FROM Car c WHERE c.driver IS NULL")
    Page<Car> findAvailable(Pageable pageable);

    boolean existsByLicenseNumber(String licenseNumber);

}
