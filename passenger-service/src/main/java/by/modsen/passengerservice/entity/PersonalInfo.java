package by.modsen.passengerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class PersonalInfo {

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "birthdate")
    private LocalDate birthDate;

}
