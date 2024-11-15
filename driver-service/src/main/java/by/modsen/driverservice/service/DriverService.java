package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;

import java.util.List;

public interface DriverService {

    List<DriverDto> getAll();

    List<DriverWithCarDto> getAllWithCar();

    DriverDto getById(Long id);

    DriverWithCarDto getWithCarById(Long id);

    DriverDto create(DriverCreateDto driverCreateDto);

    DriverDto update(Long id, DriverCreateDto driverCreateDto);

    void deleteById(Long id);

    void assignCarToDriver(long id, long carId);

    void unAssignCarFromDriver(long id);
}
