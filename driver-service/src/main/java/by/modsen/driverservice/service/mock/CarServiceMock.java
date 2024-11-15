package by.modsen.driverservice.service.mock;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceMock implements CarService {

    @Override
    public List<CarDto> getAll() {
        return List.of();
    }

    @Override
    public List<CarDto> getAvailable() {
        return List.of();
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
