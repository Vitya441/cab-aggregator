package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;

public interface DriverService {

    PaginationDto<DriverDto> getAll(int pageNumber, int pageSize);

    PaginationDto<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize);

    DriverDto getById(Long id);

    DriverWithCarDto getWithCarById(Long id);

    DriverDto create(DriverCreateDto driverCreateDto);

    DriverDto update(Long id, DriverCreateDto driverCreateDto);

    void deleteById(Long id);

    void assignCarToDriver(long id, long carId);

    void unAssignCarFromDriver(long id);
}
