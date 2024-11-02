package by.modsen.passengerservice.service;

import by.modsen.passengerservice.dto.PaginationDto;
import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;

public interface PassengerService {

    PassengerDto create(PassengerCreateDto passengerCreateDto);

    PaginationDto<PassengerDto> getAll(int pageNumber, int size);

    PassengerDto getById(long id);

    PassengerDto update(long id, PassengerCreateDto passengerCreateDto);

    void deleteById(long id);
}
