package by.modsen.driverservice.service.impl;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.request.DriverUpdateDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.DriverWithCarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.exception.CarAlreadyAssignedException;
import by.modsen.driverservice.exception.NotFoundException;
import by.modsen.driverservice.exception.PhoneExistsException;
import by.modsen.driverservice.mapper.DriverMapper;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.repository.DriverRepository;
import by.modsen.driverservice.service.DriverService;
import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final CarRepository carRepository;
    private final DriverMapper driverMapper;

    @Override
    public PaginationDto<DriverDto> getAll(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<Driver> page = driverRepository.findAll(pageRequest);
        List<DriverDto> data = page.getContent().stream()
                .map(driverMapper::toDto)
                .toList();

        return new PaginationDto<>(
                data,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public PaginationDto<DriverWithCarDto> getAllWithCar(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<Driver> page = driverRepository.findAll(pageRequest);
        List<DriverWithCarDto> data = page.getContent().stream()
                .map(driverMapper::toDriverWithCarDto)
                .toList();

        return new PaginationDto<>(
                data,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public DriverDto getById(Long id) {
        Driver driver = getDriverByIdOrThrow(id);

        return driverMapper.toDto(driver);
    }

    @Override
    public DriverWithCarDto getByIdWithCar(Long id) {
        Driver driver = getDriverByIdOrThrow(id);

        return driverMapper.toDriverWithCarDto(driver);
    }

    @Transactional
    @Override
    public DriverDto create(DriverCreateDto driverCreateDto) {
        Driver driver = driverMapper.toEntity(driverCreateDto);
        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toDto(savedDriver);
    }

    @Transactional
    @Override
    public DriverDto update(Long id, DriverUpdateDto driverUpdateDto) {
        Driver currentDriver = getDriverByIdOrThrow(id);
        if (driverUpdateDto.phone() != null && !driverUpdateDto.phone().equals(currentDriver.getPhone())) {
            if (driverRepository.existsByPhone(driverUpdateDto.phone())) {
                throw new PhoneExistsException(ExceptionMessageKeyConstants.PHONE_EXISTS);
            }
        }
        driverMapper.updateEntityFromDto(driverUpdateDto, currentDriver);
        Driver savedDriver = driverRepository.save(currentDriver);

        return driverMapper.toDto(savedDriver);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getDriverByIdOrThrow(id);
        driverRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void assignCarToDriver(long id, long carId) {
        Driver driver = getDriverByIdOrThrow(id);
        Car car = getCarByIdOrThrow(carId);
        if (car.getDriver() != null) {
            throw new CarAlreadyAssignedException(ExceptionMessageKeyConstants.CAR_ALREADY_ASSIGNED, carId);
        }
        driver.setCar(car);
        driverRepository.save(driver);
    }

    @Transactional
    @Override
    public void unAssignCarFromDriver(long id) {
        Driver driver = getDriverByIdOrThrow(id);
        driver.setCar(null);
        driverRepository.save(driver);
    }

    private Driver getDriverByIdOrThrow(long id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageKeyConstants.DRIVER_NOT_FOUND, id));
    }

    private Car getCarByIdOrThrow(long id) {
        return carRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageKeyConstants.CAR_NOT_FOUND, id));
    }
}