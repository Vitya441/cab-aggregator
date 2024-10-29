package by.modsen.passengerservice.service.impl;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.repository.PassengerRepository;
import by.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;
    private final PassengerMapper mapper;

    @Override
    public PassengerDto createPassenger(PassengerCreateDto passengerCreateDto) {
        Passenger passenger = mapper.toPassenger(passengerCreateDto);
        Passenger savedPassenger = repository.save(passenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public List<PassengerDto> getAllPassengers() {
        List<Passenger> passengers = repository.findAll();
        return mapper.toPassengerDtos(passengers);
    }

    @Override
    public PassengerDto getPassengerById(Long id) {
        Passenger passenger = repository.findById(id).orElse(null);
        return mapper.toPassengerDto(passenger);
    }

    @Override
    public PassengerDto updatePassenger(Long id, PassengerCreateDto passengerCreateDto) {
        Passenger existingPassenger = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Passenger with id " + id + " not found")
        );
        mapper.updatePassengerFromDto(passengerCreateDto, existingPassenger);
        Passenger savedPassenger = repository.save(existingPassenger);
        return mapper.toPassengerDto(savedPassenger);
    }

    @Override
    public void deletePassengerById(Long id) {
        repository.deleteById(id);
    }
}