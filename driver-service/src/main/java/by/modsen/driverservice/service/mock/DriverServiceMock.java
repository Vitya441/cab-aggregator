package by.modsen.driverservice.service.mock;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.service.DriverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceMock implements DriverService {

    @Override
    public Page<DriverDto> getAll(int pageNumber, int pageSize, Sort sort) {
        return null;
    }

    @Override
    public Page<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize, Sort sort) {
        return null;
    }

    @Override
    public DriverDto getById(Long id) {
        return null;
    }

    @Override
    public DriverWithCarDto getWithCarById(Long id) {
        return null;
    }

    @Override
    public DriverDto create(DriverCreateDto driverCreateDto) {
        return null;
    }

    @Override
    public DriverDto update(Long id, DriverCreateDto driverCreateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void assignCarToDriver(long id, long carId) {
    }

    @Override
    public void unAssignCarFromDriver(long id) {
    }
}