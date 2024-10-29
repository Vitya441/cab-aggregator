package by.modsen.passengerservice.service;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;

import java.util.List;

public interface PassengerService {

    PassengerDto createPassenger(PassengerCreateDto passengerCreateDto);

    List<PassengerDto> getAllPassengers();

    PassengerDto getPassengerById(Long id);

    PassengerDto updatePassenger(Long id, PassengerCreateDto passengerCreateDto);

    void deletePassengerById(Long id);

}
