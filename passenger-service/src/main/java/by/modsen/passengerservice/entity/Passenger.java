package by.modsen.passengerservice.entity;

import by.modsen.passengerservice.entity.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birthdate")
    private String birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod preferredPaymentMethod;

    @Column
    private BigDecimal balance;

    // Оценки пассажира (водители оценивают после поездки)
    @ElementCollection
    @CollectionTable(name = "passenger_ratings", joinColumns = @JoinColumn(name = "passenger_id"))
    @Column(name = "rating")
    private List<Integer> ratings =  new ArrayList<>();

    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = Instant.now();
    }


}
