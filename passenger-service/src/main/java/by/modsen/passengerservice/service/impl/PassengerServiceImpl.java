package by.modsen.passengerservice.service.impl;

import by.modsen.passengerservice.dto.PaginationDto;
import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerNotFoundException;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.repository.PassengerRepository;
import by.modsen.passengerservice.service.PassengerService;
import by.modsen.passengerservice.utils.MessageUtils;
import by.modsen.passengerservice.utils.PassengerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;
    private final PassengerValidator validator;

    @Override
    public PassengerDto create(PassengerCreateDto passengerCreateDto) {
        validator.validateUniqueness(passengerCreateDto);
        Passenger passenger = mapper.toPassenger(passengerCreateDto);
        Passenger savedPassenger = repository.save(passenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public PaginationDto<PassengerDto> getAll(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Passenger> page = repository.findAll(pageRequest);
        List<PassengerDto> data = page.getContent().stream()
                .map(mapper::toPassengerDto)
                .toList();

        return new PaginationDto<>(
                data,
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize()
        );
    }

    @Override
    public PassengerDto getById(long id) {
        Passenger passenger = repository
                .findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(MessageUtils.PASSENGER_NOT_FOUND_ERROR, id));
        return mapper.toPassengerDto(passenger);
    }

    @Override
    public PassengerDto update(long id, PassengerCreateDto requestDto) {
        Passenger existingPassenger = repository
                .findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(MessageUtils.PASSENGER_NOT_FOUND_ERROR, id));

        validator.validateUniqueness(requestDto, existingPassenger);
        mapper.updatePassengerFromDto(requestDto, existingPassenger);
        Passenger savedPassenger = repository.save(existingPassenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new PassengerNotFoundException(MessageUtils.PASSENGER_NOT_FOUND_ERROR, id);
        }
        repository.deleteById(id);
    }
}