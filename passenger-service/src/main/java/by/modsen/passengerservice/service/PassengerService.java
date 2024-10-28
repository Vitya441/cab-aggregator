package by.modsen.passengerservice.service;

import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public void createPassenger(Passenger passenger) {
        repository.save(passenger);
    }

    public Passenger getPassengerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Passenger> getAllPassengers() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }



}
