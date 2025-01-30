package by.modsen.driverservice.entity;

import by.modsen.driverservice.entity.enums.CarCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "car")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "driver")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "car")
    private Driver driver;

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "seats", nullable = false)
    private int seats;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_category", nullable = false)
    private CarCategory category;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return seats == car.seats && Objects.equals(id, car.id) && Objects.equals(driver, car.driver) && Objects.equals(licenseNumber, car.licenseNumber) && Objects.equals(color, car.color) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && category == car.category && Objects.equals(createdAt, car.createdAt) && Objects.equals(updatedAt, car.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, driver, licenseNumber, color, seats, brand, model, category, createdAt, updatedAt);
    }
}