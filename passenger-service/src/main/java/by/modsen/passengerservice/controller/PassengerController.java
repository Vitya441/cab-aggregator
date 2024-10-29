package by.modsen.passengerservice.controller;

import by.modsen.passengerservice.dto.PassengerCreateDto;
import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.service.impl.PassengerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerServiceImpl service;

    @PostMapping
    public PassengerDto create(@RequestBody PassengerCreateDto passengerCreateDto) {
        return service.createPassenger(passengerCreateDto);
    }

    @GetMapping
    public List<PassengerDto> getAll() {
        return service.getAllPassengers();
    }

    @GetMapping("/{id}")
    public PassengerDto getById(@PathVariable Long id) {
        return service.getPassengerById(id);
    }

    @PutMapping({"/{id}"})
    public PassengerDto update(@PathVariable Long id, @RequestBody PassengerCreateDto passengerCreateDto) {
        return service.updatePassenger(id, passengerCreateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePassengerById(id);
    }

}
