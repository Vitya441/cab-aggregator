package by.modsen.authservice.entity;

import by.modsen.authservice.util.InboxStatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InboxMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String payload;

    @Convert(converter = InboxStatusConverter.class)
    private InboxStatus status = InboxStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime receivedAt = LocalDateTime.now();
}