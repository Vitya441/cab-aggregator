package by.modsen.passengerservice.utils;

import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerWithPhoneExistsException;
import by.modsen.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerValidator {

    private final PassengerRepository repository;

    public void validateUniqueness(PassengerUpdateDto dto, Passenger currentPassenger) {
        if (!dto.phone().equals(currentPassenger.getPhone())) {
            validatePhoneUniqueness(dto.phone());
        }
    }

    private void validatePhoneUniqueness(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new PassengerWithPhoneExistsException(MessageUtils.PASSENGER_PHONE_EXIST_ERROR, phone);
        }
    }
}