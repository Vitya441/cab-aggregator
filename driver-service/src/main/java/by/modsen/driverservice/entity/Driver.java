package by.modsen.driverservice.entity;

import by.modsen.commonmodule.enumeration.DriverStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString(exclude = "car")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id", unique = true)
    private Car car;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = true)
    private String phone;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    @Column(name = "rejected_ride_id")
    private Long rejectedRideId;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

    @PrePersist
    private void prePersist() {
        status = DriverStatus.AVAILABLE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) && Objects.equals(customerId, driver.customerId) && Objects.equals(car, driver.car) && Objects.equals(firstName, driver.firstName) && Objects.equals(lastName, driver.lastName) && Objects.equals(phone, driver.phone) && status == driver.status && Objects.equals(rejectedRideId, driver.rejectedRideId) && Objects.equals(createdAt, driver.createdAt) && Objects.equals(updatedAt, driver.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, car, firstName, lastName, phone, status, rejectedRideId, createdAt, updatedAt);
    }
}