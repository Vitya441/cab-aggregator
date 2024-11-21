package by.modsen.driverservice.service.impl;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.exception.LicenseNumberExistsException;
import by.modsen.driverservice.exception.NotFoundException;
import by.modsen.driverservice.mapper.CarMapper;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.service.CarService;
import by.modsen.driverservice.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final CarMapper mapper;

    @Override
    public Page<CarDto> getAll(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Car> page = repository.findAll(pageRequest);

        return page.map(mapper::toDto);
    }

    @Override
    public Page<CarDto> getAvailable(int pageNumber, int pageSize, Sort sort) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Car> page = repository.findAvailable(pageRequest);

        return page.map(mapper::toDto);
    }

    @Override
    public CarDto getById(Long id) {
        Car car = getOrThrow(id);

        return mapper.toDto(car);
    }

    @Override
    public CarDto create(CarCreateDto carCreateDto) {
        if (repository.existsByLicenseNumber(carCreateDto.licenseNumber())) {
            throw new LicenseNumberExistsException(MessageUtil.LICENSE_NUMBER_EXISTS);
        }
        Car car = mapper.toEntity(carCreateDto);
        Car savedCar = repository.save(car);

        return mapper.toDto(savedCar);
    }

    @Override
    public CarDto update(Long id, CarCreateDto carUpdateDto) {
        Car currentCar = getOrThrow(id);
        if (!currentCar.getLicenseNumber().equals(carUpdateDto.licenseNumber()) && repository.existsByLicenseNumber(carUpdateDto.licenseNumber())) {
            throw new LicenseNumberExistsException(MessageUtil.LICENSE_NUMBER_EXISTS);
        }
        mapper.updateEntityFromDto(carUpdateDto, currentCar);
        Car savedCar = repository.save(currentCar);

        return mapper.toDto(savedCar);
    }

    @Override
    public void deleteById(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Car getOrThrow(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(MessageUtil.CAR_NOT_FOUND, id));
    }
}