package by.modsen.driverservice.unit;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.mapper.CarMapper;
import by.modsen.driverservice.mapper.CarMapperImpl;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static by.modsen.driverservice.util.CarTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Spy
    private CarMapper carMapper = new CarMapperImpl();

    @Test
    void getCarById_shouldReturnSpecifiedCar() {
        Car car = getCar();

        when(carRepository.findById(CAR_ID))
                .thenReturn(Optional.of(car));

        CarDto carResponse = carService.getById(CAR_ID);

        assertEquals(car.getId(), carResponse.id());
        assertEquals(car.getBrand(), carResponse.brand());
        assertEquals(car.getLicenseNumber(), carResponse.licenseNumber());
        verify(carRepository).findById(CAR_ID);
    }

    @Test
    void createCar_shouldReturnCreatedCarDto() {
        CarCreateDto carCreateDto = getCarCreateDto();
        Car carToSave = getCarWithoutId();
        Car savedCar = getCar();

        when(carRepository.existsByLicenseNumber(carCreateDto.licenseNumber()))
                .thenReturn(Boolean.FALSE);
        when(carRepository.save(carToSave))
                .thenReturn(savedCar);

        CarDto carResponse = carService.create(carCreateDto);

        assertEquals(carCreateDto.licenseNumber(), carResponse.licenseNumber());
        verify(carRepository).existsByLicenseNumber(carCreateDto.licenseNumber());
        verify(carRepository).save(carToSave);
    }

    @Test
    void updateCar_shouldReturnUpdatedCarDto() {
        CarCreateDto carUpdateDto = getCarUpdateDto();
        Car currentCar = getCar();
        Car updatedCar = getUpdatedCar();

        when(carRepository.findById(CAR_ID))
                .thenReturn(Optional.of(currentCar));
        when(carRepository.save(currentCar))
                .thenReturn(updatedCar);

        CarDto carResponse = carService.update(CAR_ID, carUpdateDto);

        assertEquals(carUpdateDto.licenseNumber(), carResponse.licenseNumber());
        assertEquals(carUpdateDto.category(), carResponse.category());
        verify(carRepository).findById(CAR_ID);
        verify(carRepository).save(currentCar);
    }

    @Test
    void deleteCarById_shouldReturnNothing() {
        Car car = getCar();

        when(carRepository.findById(CAR_ID))
                .thenReturn(Optional.of(car));
        doNothing()
                .when(carRepository).deleteById(CAR_ID);

        carService.deleteById(CAR_ID);

        verify(carRepository).findById(CAR_ID);
        verify(carRepository).deleteById(CAR_ID);
    }

    @Test
    void getAllCars_shouldReturnPage() {
        List<Car> cars = getCarList();
        List<CarDto> carDtos = getCarDtoList();
        Page<Car> carPage = getPage();
        PageRequest pageRequest = getPageRequest();

        when(carRepository.findAll(pageRequest))
                .thenReturn(carPage);

        PaginationDto<CarDto> result = carService.getAll(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD);

        assertEquals(PAGE_NUMBER, result.pageNumber());
        assertEquals(PAGE_SIZE, result.pageSize());
        assertEquals(TOTAL_ELEMENTS, result.totalElements());
        assertEquals(TOTAL_PAGES, result.totalPages());
        assertEquals(carDtos, result.content());

        verify(carRepository).findAll(pageRequest);
    }

    @Test
    void getAvailableCars_shouldReturnPage() {
        List<CarDto> carDtos = getCarDtoList();
        Page<Car> carPage = getPage();
        PageRequest pageRequest = getPageRequest();

        when(carRepository.findCarByDriverIsNull(pageRequest))
                .thenReturn(carPage);

        PaginationDto<CarDto> result = carService.getAvailable(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD);

        assertEquals(PAGE_NUMBER, result.pageNumber());
        assertEquals(PAGE_SIZE, result.pageSize());
        assertEquals(TOTAL_ELEMENTS, result.totalElements());
        assertEquals(TOTAL_PAGES, result.totalPages());
        assertEquals(carDtos, result.content());

        verify(carRepository).findCarByDriverIsNull(pageRequest);
    }
}