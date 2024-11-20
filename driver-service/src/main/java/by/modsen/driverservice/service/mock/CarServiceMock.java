package by.modsen.driverservice.service.mock;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CarServiceMock implements CarService {

    @Override
    public Page<CarDto> getAll(int pageNumber, int pageSize, Sort sort) {
        return null;
    }

    @Override
    public Page<CarDto> getAvailable(int pageNumber, int pageSize, Sort sort) {
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
