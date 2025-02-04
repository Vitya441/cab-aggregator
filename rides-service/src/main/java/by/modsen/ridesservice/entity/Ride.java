package by.modsen.ridesservice.entity;

import by.modsen.commonmodule.enumeration.PaymentMethod;
import by.modsen.commonmodule.enumeration.RideStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long passengerId;

    private Long driverId;

    @Column(nullable = false)
    private String pickupAddress;

    @Column(nullable = false)
    private String destinationAddress;

    private BigDecimal estimatedCost;

    private BigDecimal actualCost;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private boolean ratedByPassenger;

    private boolean ratedByDriver;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.status = RideStatus.REQUESTED;
    }

    public Long getDuration() {
        return Duration.between(startTime, endTime).toMinutes();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return ratedByPassenger == ride.ratedByPassenger && ratedByDriver == ride.ratedByDriver && Objects.equals(id, ride.id) && Objects.equals(passengerId, ride.passengerId) && Objects.equals(driverId, ride.driverId) && Objects.equals(pickupAddress, ride.pickupAddress) && Objects.equals(destinationAddress, ride.destinationAddress) && Objects.equals(estimatedCost, ride.estimatedCost) && Objects.equals(actualCost, ride.actualCost) && status == ride.status && paymentMethod == ride.paymentMethod && Objects.equals(startTime, ride.startTime) && Objects.equals(endTime, ride.endTime) && Objects.equals(createdAt, ride.createdAt) && Objects.equals(updatedAt, ride.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengerId, driverId, pickupAddress, destinationAddress, estimatedCost, actualCost, status, ratedByPassenger, ratedByDriver, paymentMethod, startTime, endTime, createdAt, updatedAt);
    }
}