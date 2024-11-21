package by.modsen.driverservice.service.impl;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.exception.CarAlreadyAssignedException;
import by.modsen.driverservice.exception.NotFoundException;
import by.modsen.driverservice.exception.PhoneExistsException;
import by.modsen.driverservice.mapper.DriverMapper;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.repository.DriverRepository;
import by.modsen.driverservice.service.DriverService;
import by.modsen.driverservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final DriverMapper driverMapper;

    @Override
    public Page<DriverDto> getAll(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Driver> page = driverRepository.findAll(pageRequest);

        return page.map(driverMapper::toDto);
    }

    @Override
    public Page<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Driver> page = driverRepository.findAll(pageRequest);

        return page.map(driverMapper::toDriverWithCarDto);
    }

    @Override
    public DriverDto getById(Long id) {
        Driver driver = getDriverOrThrow(id);

        return driverMapper.toDto(driver);
    }

    @Override
    public DriverWithCarDto getWithCarById(Long id) {
        Driver driver = getDriverOrThrow(id);

        return driverMapper.toDriverWithCarDto(driver);
    }

    @Override
    public DriverDto create(DriverCreateDto driverCreateDto) {
        if (driverRepository.existsByPhone(driverCreateDto.phone())) {
            throw new PhoneExistsException(MessageUtil.PHONE_EXISTS);
        }
        Driver driver = driverMapper.toEntity(driverCreateDto);
        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toDto(savedDriver);
    }

    @Override
    public DriverDto update(Long id, DriverCreateDto driverUpdateDto) {
        Driver currentDriver = getDriverOrThrow(id);
        if (!currentDriver.getPhone().equals(driverUpdateDto.phone()) && driverRepository.existsByPhone(driverUpdateDto.phone())) {
            throw new PhoneExistsException(MessageUtil.PHONE_EXISTS);
        }
        driverMapper.updateEntityFromDto(driverUpdateDto, currentDriver);
        Driver savedDriver = driverRepository.save(currentDriver);

        return driverMapper.toDto(savedDriver);
    }

    @Override
    public void deleteById(Long id) {
        getDriverOrThrow(id);
        driverRepository.deleteById(id);
    }

    @Override
    public void assignCarToDriver(long id, long carId) {
        Driver driver = getDriverOrThrow(id);
        Car car = getCarOrThrow(carId);
        if (car.getDriver() != null) {
            throw new CarAlreadyAssignedException(MessageUtil.CAR_ALREADY_ASSIGNED, carId);
        }
        driver.setCar(car);
        driverRepository.save(driver);
    }

    @Override
    public void unAssignCarFromDriver(long id) {
        Driver driver = getDriverOrThrow(id);
        driver.setCar(null);
        driverRepository.save(driver);
    }

    private Driver getDriverOrThrow(long id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageUtil.DRIVER_NOT_FOUND, id));
    }

    private Car getCarOrThrow(long id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageUtil.CAR_NOT_FOUND, id));
    }
}