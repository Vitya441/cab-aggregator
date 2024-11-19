package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import org.springframework.data.domain.Sort;

public interface DriverService {

    PaginationDto<DriverDto> getAll(int pageNumber, int pageSize, Sort sort);

    PaginationDto<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize, Sort sort);

    DriverDto getById(Long id);

    DriverWithCarDto getWithCarById(Long id);

    DriverDto create(DriverCreateDto driverCreateDto);

    DriverDto update(Long id, DriverCreateDto driverCreateDto);

    void deleteById(Long id);

    void assignCarToDriver(long id, long carId);

    void unAssignCarFromDriver(long id);
}
