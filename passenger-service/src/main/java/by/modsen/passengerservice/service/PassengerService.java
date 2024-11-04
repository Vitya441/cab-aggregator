package by.modsen.passengerservice.service;

import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.response.PassengerDto;

public interface PassengerService {

    PassengerDto create(PassengerCreateDto passengerCreateDto);

    PaginationDto<PassengerDto> getAll(int pageNumber, int pageSize);

    PassengerDto getById(long id);

    PassengerDto update(long id, PassengerUpdateDto passengerUpdateDto);

    void deleteById(long id);
}