package by.modsen.passengerservice.service.impl;

import by.modsen.passengerservice.dto.PaginationDto;
import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerNotFoundException;
import by.modsen.passengerservice.exception.PassengerWithEmailExistsException;
import by.modsen.passengerservice.exception.PassengerWithPhoneExistsException;
import by.modsen.passengerservice.exception.PassengerWithUsernameExistsException;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.repository.PassengerRepository;
import by.modsen.passengerservice.service.PassengerService;
import by.modsen.passengerservice.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;
    private final MessageUtils messageUtils;

    @Override
    public PassengerDto create(PassengerCreateDto passengerCreateDto) {
        validatePassengerUniqueness(passengerCreateDto);
        Passenger passenger = mapper.toPassenger(passengerCreateDto);
        Passenger savedPassenger = repository.save(passenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public PaginationDto<PassengerDto> getAll(Pageable pageable) {
        Page<Passenger> page = repository.findAll(pageable);
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
                .orElseThrow(() -> new PassengerNotFoundException(messageUtils.getMessage("passenger.notFound", id)));
        return mapper.toPassengerDto(passenger);
    }

    @Override
    public PassengerDto update(long id, PassengerCreateDto requestDto) {
        Passenger existingPassenger = repository
                .findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(messageUtils.getMessage("passenger.notFound")));

        validatePassengerUniqueness(requestDto, existingPassenger);

        mapper.updatePassengerFromDto(requestDto, existingPassenger);
        Passenger savedPassenger = repository.save(existingPassenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new PassengerNotFoundException(messageUtils.getMessage("passenger.notFound", id));
        }
        repository.deleteById(id);
    }

    private void validatePassengerUniqueness(PassengerCreateDto dto) {
        if (repository.existsByUsername(dto.username())) throw new PassengerWithUsernameExistsException(messageUtils.getMessage("passenger.usernameExists", dto.username()));
        if (repository.existsByEmail(dto.email())) throw new PassengerWithEmailExistsException(messageUtils.getMessage("passenger.emailExists", dto.email()));
        if (repository.existsByPhone(dto.phone())) throw new PassengerWithPhoneExistsException(messageUtils.getMessage("passenger.phoneExists", dto.phone()));
    }

    private void validatePassengerUniqueness(PassengerCreateDto dto, Passenger existingPassenger) {
        if (!existingPassenger.getUsername().equals(dto.username()) && repository.existsByUsername(dto.username())) {
            throw new PassengerWithUsernameExistsException(messageUtils.getMessage("passenger.usernameExists", dto.username()));
        }
        if (!existingPassenger.getEmail().equals(dto.email()) && repository.existsByEmail(dto.email())) {
            throw new PassengerWithEmailExistsException(messageUtils.getMessage("passenger.emailExists", dto.email()));
        }
        if (!existingPassenger.getPhone().equals(dto.phone()) && repository.existsByPhone(dto.phone())) {
            throw new PassengerWithPhoneExistsException(messageUtils.getMessage("passenger.phoneExists", dto.phone()));
        }
    }
}