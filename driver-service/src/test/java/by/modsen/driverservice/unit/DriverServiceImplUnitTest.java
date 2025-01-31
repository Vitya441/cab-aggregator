package by.modsen.driverservice.unit;

import by.modsen.driverservice.client.PaymentClient;
import by.modsen.driverservice.client.RatingClient;
import by.modsen.driverservice.dto.request.CustomerRequest;
import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.request.DriverUpdateDto;
import by.modsen.driverservice.dto.response.CustomerResponse;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.exception.CarAlreadyAssignedException;
import by.modsen.driverservice.exception.NotFoundException;
import by.modsen.driverservice.mapper.DriverMapper;
import by.modsen.driverservice.mapper.DriverMapperImpl;
import by.modsen.driverservice.repository.CarRepository;
import by.modsen.driverservice.repository.DriverRepository;
import by.modsen.driverservice.service.impl.DriverServiceImpl;
import by.modsen.driverservice.util.ExceptionMessageKeyConstants;
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

import static by.modsen.driverservice.util.CarTestConstants.TOTAL_PAGES;
import static by.modsen.driverservice.util.DriverTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplUnitTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private RatingClient ratingClient;

    @Mock
    private CarRepository carRepository;

    @Spy
    private DriverMapper driverMapper = new DriverMapperImpl();

    @Test
    void getDriverById_shouldReturnDriver_whenDriverExists() {
        Driver driver = getSavedDriver();

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(driver));

        DriverDto driverResponse = driverService.getById(DRIVER_ID);

        assertNotNull(driverResponse);
        assertEquals(driver.getFirstName(), driverResponse.firstName());
        assertEquals(driver.getId(), driverResponse.id());
        verify(driverRepository).findById(DRIVER_ID);
    }

    @Test
    void getDriverById_shouldThrowException() {
        when(driverRepository.findById(INVALID_ID))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> driverService.getById(INVALID_ID)
        );

        assertEquals(ExceptionMessageKeyConstants.DRIVER_NOT_FOUND, exception.getMessage());
        verify(driverRepository).findById(INVALID_ID);
    }

    @Test
    void createDriver_shouldReturnCreatedDriver() {
        DriverCreateDto createDto = getDriverCreateDto();
        Driver driver = getDriverToSave();
        Driver savedDriver = getSavedDriver();
        DriverDto driverDto = getDriverDto();
        CustomerRequest customerRequest = getCustomerRequest();
        CustomerResponse customerResponse = getCustomerResponse();

        when(driverRepository.save(driver))
                .thenReturn(savedDriver);
        when(paymentClient.createCustomer(customerRequest))
                .thenReturn(customerResponse);
        doNothing()
                .when(ratingClient).createDriverRatingRecord(DRIVER_ID);

        DriverDto driverResponse = driverService.create(createDto);

        assertNotNull(driverResponse);
        assertEquals(driverDto.customerId(), driverResponse.customerId());
        verify(driverRepository).save(driver);
        verify(paymentClient).createCustomer(customerRequest);
        verify(ratingClient).createDriverRatingRecord(DRIVER_ID);
    }

    @Test
    void updateDriver_shouldReturnUpdatedDriver() {
        DriverUpdateDto updateDto = getDriverUpdateDto();
        Driver savedDriver = getSavedDriver();

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(savedDriver));
        when(driverRepository.save(savedDriver))
                .thenReturn(savedDriver);

        DriverDto driverResponse = driverService.update(DRIVER_ID, updateDto);
        assertNotNull(driverResponse);
        assertEquals(updateDto.phone(), driverResponse.phone());
        verify(driverRepository).findById(DRIVER_ID);
        verify(driverRepository).save(savedDriver);
    }

    @Test
    void deleteDriverById_shouldSuccessfullyDeleteDriver() {
        Driver driver = getSavedDriver();

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(driver));
        doNothing()
                .when(driverRepository).deleteById(DRIVER_ID);

        driverService.deleteById(DRIVER_ID);

        verify(driverRepository).findById(DRIVER_ID);
        verify(driverRepository).deleteById(DRIVER_ID);
    }

    @Test
    void deleteDriverById_shouldThrowException() {
        when(driverRepository.findById(INVALID_ID))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> driverService.deleteById(INVALID_ID)
        );

        assertEquals(ExceptionMessageKeyConstants.DRIVER_NOT_FOUND, exception.getMessage());
        verify(driverRepository).findById(INVALID_ID);
    }

    @Test
    void assignCarToDriver() {
        Driver driverWithoutCar = getDriverWithoutCar();
        Driver driverWithCar = getDriverWithCar();
        Car availableCar = getCar();

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(driverWithoutCar));
        when(carRepository.findById(CAR_ID))
                .thenReturn(Optional.of(availableCar));
        when(driverRepository.save(driverWithCar))
                .thenReturn(driverWithCar);

        driverService.assignCarToDriver(DRIVER_ID, CAR_ID);

        verify(driverRepository).findById(DRIVER_ID);
        verify(carRepository).findById(CAR_ID);
        verify(driverRepository).save(driverWithCar);
    }

    @Test
    void assignCarToDriver_whenCarAlreadyAssignedToAnotherDriver() {
        Driver driverWithoutCar = getDriverWithoutCar();
        Driver anotherDriverWithoutCar = getAnotherDriverWithoutCar();
        Car car = getCar();
        car.setDriver(anotherDriverWithoutCar);

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(driverWithoutCar));
        when(carRepository.findById(CAR_ID))
                .thenReturn(Optional.of(car));

        CarAlreadyAssignedException exception = assertThrows(
                CarAlreadyAssignedException.class,
                () -> driverService.assignCarToDriver(DRIVER_ID, CAR_ID)
        );

        assertEquals(ExceptionMessageKeyConstants.CAR_ALREADY_ASSIGNED, exception.getMessage());
        verify(driverRepository).findById(DRIVER_ID);
        verify(carRepository).findById(CAR_ID);
    }

    @Test
    void unassignCarToDriver() {
        Driver driverWithoutCar = getDriverWithoutCar();
        Driver driverWithCar = getDriverWithCar();

        when(driverRepository.findById(DRIVER_ID))
                .thenReturn(Optional.of(driverWithCar));
        when(driverRepository.save(driverWithoutCar))
                .thenReturn(driverWithoutCar);

        driverService.unAssignCarFromDriver(DRIVER_ID);

        verify(driverRepository).findById(DRIVER_ID);
        verify(driverRepository).save(driverWithoutCar);
    }

    @Test
    void getAllDrivers_shouldReturnPage() {
        List<DriverDto> driverDtos = getDriverDtoList();
        Page<Driver> driverPage = getPage();
        PageRequest pageRequest = getPageRequest();

        when(driverRepository.findAll(pageRequest))
                .thenReturn(driverPage);

        PaginationDto<DriverDto> result = driverService.getAll(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD);

        assertEquals(PAGE_NUMBER, result.pageNumber());
        assertEquals(PAGE_SIZE, result.pageSize());
        assertEquals(TOTAL_ELEMENTS, result.totalElements());
        assertEquals(TOTAL_PAGES, result.totalPages());
        assertEquals(driverDtos, result.content());

        verify(driverRepository).findAll(pageRequest);
    }
}