package by.modsen.ridesservice.service;

import by.modsen.ridesservice.dto.response.PassengerDto;

public interface PassengerService {
    PassengerDto getPassengerById(Long id);
}