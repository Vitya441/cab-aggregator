package by.modsen.passengerservice.utils;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerWithEmailExistsException;
import by.modsen.passengerservice.exception.PassengerWithPhoneExistsException;
import by.modsen.passengerservice.exception.PassengerWithUsernameExistsException;
import by.modsen.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerValidator {

    private final PassengerRepository repository;

    public void validateUniqueness(PassengerCreateDto dto) {
        if (repository.existsByUsername(dto.username())) {
            throw new PassengerWithUsernameExistsException(MessageUtils.PASSENGER_USERNAME_EXIST_ERROR, dto.username());
        }
        if (repository.existsByEmail(dto.email())) {
            throw new PassengerWithEmailExistsException(MessageUtils.PASSENGER_EMAIL_EXIST_ERROR, dto.email());
        }
        if (repository.existsByPhone(dto.phone())) {
            throw new PassengerWithPhoneExistsException(MessageUtils.PASSENGER_PHONE_EXIST_ERROR, dto.phone());
        }
    }

    public void validateUniqueness(PassengerCreateDto dto, Passenger existingPassenger) {
        if (!existingPassenger.getUsername().equals(dto.username()) && repository.existsByUsername(dto.username())) {
            throw new PassengerWithUsernameExistsException(MessageUtils.PASSENGER_USERNAME_EXIST_ERROR, dto.username());
        }
        if (!existingPassenger.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw new PassengerWithEmailExistsException(MessageUtils.PASSENGER_EMAIL_EXIST_ERROR, dto.email());
        }
        if (!existingPassenger.getPhone().equals(dto.phone()) && repository.existsByPhone(dto.phone())) {
            throw new PassengerWithPhoneExistsException(MessageUtils.PASSENGER_PHONE_EXIST_ERROR, dto.phone());
        }
    }
}
