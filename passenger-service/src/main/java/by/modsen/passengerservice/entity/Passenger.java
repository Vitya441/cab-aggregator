package by.modsen.passengerservice.entity;

import by.modsen.passengerservice.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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

    @Size(min = 8)
    @Column(nullable = false)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private PersonalInfo info;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod preferredPaymentMethod;

    @Column
    private BigDecimal balance;

    @ElementCollection
    @CollectionTable(name = "passenger_ratings", joinColumns = @JoinColumn(name = "passenger_id"))
    @Column(name = "rating")
    private List<Integer> ratings =  new ArrayList<>();

    @Column(name = "created_utc")
    private Instant createdAt;

    @Column(name = "updated_utc")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    @PreUpdate void onUpdate() {
        this.updatedAt = Instant.now();
    }


}
