package by.modsen.passengerservice.service;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper mapper;

    public void createPassenger(PassengerCreateDto passengerCreateDto) {
        Passenger passenger = mapper.toPassenger(passengerCreateDto);
        passengerRepository.save(passenger);
    }

    public List<PassengerDto> getAllPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        return mapper.toPassengerDtos(passengers);
    }

    public PassengerDto getPassengerById(Long id) {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        return mapper.toPassengerDto(passenger);
    }

    public void deletePassengerById(Long id) {
        passengerRepository.deleteById(id);
    }
}