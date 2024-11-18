package by.modsen.driverservice.service.mock;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.service.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceMock implements CarService {

    @Override
    public PaginationDto<CarDto> getAll(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public PaginationDto<CarDto> getAvailable(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public CarDto getById(Long id) {
        return null;
    }

    @Override
    public CarDto create(CarCreateDto carCreateDto) {
        return null;
    }

    @Override
    public CarDto update(Long id, CarCreateDto carCreateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
