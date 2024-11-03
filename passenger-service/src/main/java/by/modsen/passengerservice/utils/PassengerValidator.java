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
        validateUsernameUniqueness(dto.username());
        validateEmailUniqueness(dto.email());
        validatePhoneUniqueness(dto.phone());
    }

    public void validateUniqueness(PassengerCreateDto dto, Passenger currentPassenger) {
        if (!currentPassenger.getUsername().equals(dto.username())) {
            validateUsernameUniqueness(dto.username());
        }
        if (!currentPassenger.getEmail().equals(dto.email())) {
            validateEmailUniqueness(dto.email());
        }
        if (!currentPassenger.getPhone().equals(dto.phone())) {
            validatePhoneUniqueness(dto.phone());
        }
    }

    private void validateUsernameUniqueness(String username) {
        if (repository.existsByUsername(username)) {
            throw new PassengerWithUsernameExistsException(MessageUtils.PASSENGER_USERNAME_EXIST_ERROR, username);
        }
    }

    private void validateEmailUniqueness(String email) {
        if (repository.existsByEmail(email)) {
            throw new PassengerWithEmailExistsException(MessageUtils.PASSENGER_EMAIL_EXIST_ERROR, email);
        }
    }

    private void validatePhoneUniqueness(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new PassengerWithPhoneExistsException(MessageUtils.PASSENGER_PHONE_EXIST_ERROR, phone);
        }
    }
}
