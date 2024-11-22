package by.modsen.driverservice.service.impl;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.exception.LicenseNumberExistsException;
import by.modsen.driverservice.exception.NotFoundException;
import by.modsen.driverservice.mapper.CarMapper;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.service.CarService;
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
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final CarMapper mapper;

    @Override
    public PaginationDto<CarDto> getAll(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<Car> page = repository.findAll(pageRequest);
        List<CarDto> data = page.getContent().stream()
                .map(mapper::toDto)
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
    public PaginationDto<CarDto> getAvailable(int pageNumber, int pageSize, String sortField) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortField));
        Page<Car> page = repository.findCarByDriverIsNull(pageRequest);
        List<CarDto> data = page.getContent().stream()
                .map(mapper::toDto)
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
    public CarDto getById(Long id) {
        Car car = getCarByIdOrThrow(id);

        return mapper.toDto(car);
    }

    @Transactional
    @Override
    public CarDto create(CarCreateDto carCreateDto) {
        if (repository.existsByLicenseNumber(carCreateDto.licenseNumber())) {
            throw new LicenseNumberExistsException(ExceptionMessageKeyConstants.LICENSE_NUMBER_EXISTS);
        }
        Car car = mapper.toEntity(carCreateDto);
        Car savedCar = repository.save(car);

        return mapper.toDto(savedCar);
    }

    @Transactional
    @Override
    public CarDto update(Long id, CarCreateDto carUpdateDto) {
        Car currentCar = getCarByIdOrThrow(id);
        if (!currentCar.getLicenseNumber().equals(carUpdateDto.licenseNumber()) && repository.existsByLicenseNumber(carUpdateDto.licenseNumber())) {
            throw new LicenseNumberExistsException(ExceptionMessageKeyConstants.LICENSE_NUMBER_EXISTS);
        }
        mapper.updateEntityFromDto(carUpdateDto, currentCar);
        Car savedCar = repository.save(currentCar);

        return mapper.toDto(savedCar);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        getCarByIdOrThrow(id);
        repository.deleteById(id);
    }

    private Car getCarByIdOrThrow(long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageKeyConstants.CAR_NOT_FOUND, id));
    }
}