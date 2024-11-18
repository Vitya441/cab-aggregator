package by.modsen.driverservice.service.mock;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.service.DriverService;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceMock implements DriverService {

    @Override
    public PaginationDto<DriverDto> getAll(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public PaginationDto<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize) {
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