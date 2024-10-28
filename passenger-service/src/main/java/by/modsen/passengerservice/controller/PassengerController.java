package by.modsen.passengerservice.controller;

import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @PostMapping
    public void create(@RequestBody Passenger passenger) {
        service.createPassenger(passenger);
    }

    @GetMapping
    public List<Passenger> getAll() {
        return service.getAllPassengers();
    }

}
