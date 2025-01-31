package by.modsen.driverservice.util;

import by.modsen.commonmodule.enumeration.DriverStatus;
import by.modsen.driverservice.dto.request.CustomerRequest;
import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.request.DriverUpdateDto;
import by.modsen.driverservice.dto.response.CustomerResponse;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static by.modsen.driverservice.util.CarTestConstants.*;

public final class DriverTestConstants {

    public static final Long DRIVER_ID = 1L;
    public static final Long DRIVER_ID_NEW = 2L;
    public static final Long DRIVER_ID_3 = 3L;
    public static final Long INVALID_ID = 999L;
    public static final String CUSTOMER_ID = "cus_12345";
    public static final String CUSTOMER_ID_2 = "cus_31541";
    public static final String CUSTOMER_ID_NEW = "cus_123_xyz";
    public static final Long CAR_ID = 100L;
    public static final String FIRST_NAME = "Viktor";
    public static final String FIRST_NAME_2 = "Jane";
    public static final String LAST_NAME = "Maksimov";
    public static final String LAST_NAME_2 = "Smith";
    public static final String PHONE = "+375291234567";
    public static final String PHONE_2 = "+375295234861";
    public static final String PHONE_3 = "+375291111111";
    public static final DriverStatus STATUS = DriverStatus.AVAILABLE;
    public static final Long BALANCE = 1000L;

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 2;
    public static final int TOTAL_ELEMENTS = 2;
    public static final int TOTAL_PAGES = 1;
    public static final String SORT_FIELD = "id";

    public static final String DRIVER_PATH = "/api/v1/drivers";
    public static final String RATING_PATH = "/api/v1/ratings/drivers";
    public static final String PAYMENT_PATH = "/api/v1/payments/customers";

    private DriverTestConstants() {}

    public static Driver getDriverToSave() {
        return Driver.builder()
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static Driver getSavedDriver() {
        return Driver.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .phone(PHONE)
                .build();
    }

    public static Driver getDriverWithoutCar() {
        return Driver.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .phone(PHONE)
                .build();
    }

    public static Driver getAnotherDriverWithoutCar() {
        return Driver.builder()
                .id(DRIVER_ID_NEW)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .phone(PHONE)
                .build();
    }

    public static Driver getDriverWithCar() {
        return Driver.builder()
                .id(DRIVER_ID)
                .car(getCar())
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .phone(PHONE)
                .build();
    }

    public static Driver getDriverWithoutId() {
        return Driver.builder()
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .build();
    }

    public static Driver getUpdatedDriver() {
        return Driver.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static Driver getDriverWithCustomerId() {
        return Driver.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static Car getCar() {
        return Car.builder()
                .id(CAR_ID)
                .licenseNumber(LICENSE_NUMBER)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static DriverCreateDto getDriverCreateDto() {
        return DriverCreateDto.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .build();
    }

    public static DriverUpdateDto getDriverUpdateDto() {
        return new DriverUpdateDto(PHONE_3);
    }

    public static DriverDto getDriverDto() {
        return DriverDto.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static Driver getDriverEntityFromDb() {
        return Driver.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static DriverDto getDriverFromDb() {
        return DriverDto.builder()
                .id(DRIVER_ID)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static DriverDto getSecondDriverFromDb() {
        return DriverDto.builder()
                .id(DRIVER_ID_NEW)
                .customerId(CUSTOMER_ID_2)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .phone(PHONE_2)
                .status(STATUS)
                .build();
    }

    public static DriverDto getWireMockDriver() {
        return DriverDto.builder()
                .id(DRIVER_ID_3)
                .customerId(CUSTOMER_ID_NEW)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .status(STATUS)
                .build();
    }

    public static DriverDto getAnotherDriverDto() {
        return DriverDto.builder()
                .id(DRIVER_ID_NEW)
                .customerId(CUSTOMER_ID)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .status(STATUS)
                .build();
    }

    public static CustomerRequest getCustomerRequest() {
        return CustomerRequest.builder()
                .name(FIRST_NAME)
                .balance(BALANCE)
                .build();
    }

    public static CustomerResponse getCustomerResponse() {
        return CustomerResponse.builder()
                .customerId(CUSTOMER_ID)
                .name(FIRST_NAME)
                .balance(BALANCE)
                .build();
    }

    public static List<DriverDto> getDriverDtoList() {
        return List.of(getDriverDto(), getAnotherDriverDto());
    }

    public static PaginationDto<DriverDto> getPaginationDto() {
        return new PaginationDto<DriverDto>(
                getDriverDtoList(),
                PAGE_NUMBER,
                PAGE_SIZE,
                TOTAL_ELEMENTS,
                TOTAL_PAGES
        );
    }

    public static PageRequest getPageRequest() {
        return PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.by(Sort.Direction.ASC, SORT_FIELD));
    }

    public static Page<Driver> getPage() {
        return new PageImpl<>(getDriverList(), getPageRequest(), TOTAL_ELEMENTS);
    }

    private static List<Driver> getDriverList() {
        return List.of(getDriverWithoutCar(), getAnotherDriverWithoutCar());
    }
}