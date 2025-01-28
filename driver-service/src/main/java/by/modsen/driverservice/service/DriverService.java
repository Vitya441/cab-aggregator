package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.request.DriverUpdateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;

public interface DriverService {

    PaginationDto<DriverDto> getAll(int pageNumber, int pageSize, String sortField);

    PaginationDto<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize, String sortField);

    DriverDto getById(Long id);

    DriverWithCarDto getByIdWithCar(Long id);

    DriverDto create(DriverCreateDto driverCreateDto);

    DriverDto update(Long id, DriverUpdateDto driverUpdateDto);

    void deleteById(Long id);

    void assignCarToDriver(long id, long carId);

    void unAssignCarFromDriver(long id);
}