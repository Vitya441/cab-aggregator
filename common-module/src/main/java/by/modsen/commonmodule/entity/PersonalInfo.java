package by.modsen.commonmodule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter @Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birthdate")
    private LocalDate dateOfBirth;

}

