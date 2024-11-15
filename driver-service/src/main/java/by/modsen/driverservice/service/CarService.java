package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> getAll();

    List<CarDto> getAvailable();

    CarDto getById(Long id);

    CarDto create(CarCreateDto carCreateDto);

    CarDto update(Long id, CarCreateDto carCreateDto);

    void deleteById(Long id);
}
