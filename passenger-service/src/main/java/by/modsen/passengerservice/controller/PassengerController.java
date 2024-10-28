package by.modsen.passengerservice.controller;

import by.modsen.passengerservice.dto.PassengerDto;
import by.modsen.passengerservice.dto.PassengerCreateDto;
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
    public void create(@RequestBody PassengerCreateDto passengerCreateDto) {
        service.createPassenger(passengerCreateDto);
    }

    @GetMapping
    public List<PassengerDto> getAll() {
        return service.getAllPassengers();
    }

    @GetMapping("{id}")
    public PassengerDto getById(@PathVariable Long id) {
        return service.getPassengerById(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deletePassengerById(id);
    }

}
