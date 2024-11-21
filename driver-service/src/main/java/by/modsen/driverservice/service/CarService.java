package by.modsen.driverservice.service;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CarService {

    Page<CarDto> getAll(int pageNumber, int pageSize, Sort sort);

    Page<CarDto> getAvailable(int pageNumber, int pageSize, Sort sort);

    CarDto getById(Long id);

    CarDto create(CarCreateDto carCreateDto);

    CarDto update(Long id, CarCreateDto carCreateDto);

    void deleteById(Long id);

}