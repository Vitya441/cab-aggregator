package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.PaginationDto;

public interface CarService {

    PaginationDto<CarDto> getAll(int pageNumber, int pageSize);

    PaginationDto<CarDto> getAvailable(int pageNumber, int pageSize);

    CarDto getById(Long id);

    CarDto create(CarCreateDto carCreateDto);

    CarDto update(Long id, CarCreateDto carCreateDto);

    void deleteById(Long id);
}
