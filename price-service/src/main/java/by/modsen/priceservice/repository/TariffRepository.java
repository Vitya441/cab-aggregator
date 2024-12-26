package by.modsen.priceservice.repository;

import by.modsen.commonmodule.enumeration.CarCategory;
import by.modsen.priceservice.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findByCarCategory(CarCategory carCategory);

}