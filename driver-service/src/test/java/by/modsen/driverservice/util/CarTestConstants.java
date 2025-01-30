package by.modsen.driverservice.util;

import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.PaginationDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.enums.CarCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public final class CarTestConstants {

    public static final Long CAR_ID = 1L;
    public static final Long CAR_ID_DB = 1L;
    public static final Long CAR_ID_DB_2 = 2L;
    public static final Long CAR_ID_NEW = 2L;
    public static final String LICENSE_NUMBER = "QWE123";
    public static final String LICENSE_NUMBER_DB = "ABC123";
    public static final String LICENSE_NUMBER_DB_2 = "XYZ789";
    public static final String LICENSE_NUMBER_NEW = "XYZ123";
    public static final String COLOR = "Gray";
    public static final String COLOR_DB = "Black";
    public static final String COLOR_DB_2 = "White";
    public static final int SEATS = 3;
    public static final int SEATS_DB = 4;
    public static final String BRAND = "Audi";
    public static final String BRAND_DB = "Toyota";
    public static final String BRAND_DB_2 = "Honda";
    public static final String MODEL = "A5";
    public static final String MODEL_DB = "Camry";
    public static final String MODEL_DB_2 = "Civic";
    public static final CarCategory CATEGORY = CarCategory.COMFORT;
    public static final CarCategory CATEGORY_DB = CarCategory.ECONOMY;

    public static final int PAGE_NUMBER = 0;
    public static final int PAGE_SIZE = 2;
    public static final int TOTAL_ELEMENTS = 2;
    public static final int TOTAL_PAGES = 1;
    public static final String SORT_FIELD = "id";

    public static final String CAR_PATH = "/api/v1/cars";

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

    public static Car getAnotherCar() {
        return Car.builder()
                .id(CAR_ID_NEW)
                .licenseNumber(LICENSE_NUMBER_NEW)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static Car getUpdatedCar() {
        return Car.builder()
                .id(CAR_ID)
                .licenseNumber(LICENSE_NUMBER_NEW)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static Car getCarWithoutId() {
        return Car.builder()
                .licenseNumber(LICENSE_NUMBER)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static CarCreateDto getCarCreateDto() {
        return CarCreateDto.builder()
                .licenseNumber(LICENSE_NUMBER)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static CarCreateDto getCarUpdateDto() {
        return CarCreateDto.builder()
                .licenseNumber(LICENSE_NUMBER_NEW)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static CarDto getCarDto() {
        return CarDto.builder()
                .id(CAR_ID)
                .licenseNumber(LICENSE_NUMBER)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static CarDto getAnotherCarDto() {
        return CarDto.builder()
                .id(CAR_ID_NEW)
                .licenseNumber(LICENSE_NUMBER_NEW)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static CarDto getUpdatedCarDto() {
        return CarDto.builder()
                .id(CAR_ID)
                .licenseNumber(LICENSE_NUMBER_NEW)
                .color(COLOR)
                .seats(SEATS)
                .brand(BRAND)
                .model(MODEL)
                .category(CATEGORY)
                .build();
    }

    public static List<Car> getCarList() {
        return List.of(getCar(), getAnotherCar());
    }

    public static List<CarDto> getCarDtoList() {
        return List.of(getCarDto(), getAnotherCarDto());
    }

    public static Car getFirstCarEntityFromDb() {
        return Car.builder()
                .id(1L)
                .licenseNumber(LICENSE_NUMBER_DB)
                .color(COLOR_DB)
                .seats(SEATS_DB)
                .brand(BRAND_DB)
                .model(MODEL_DB)
                .category(CATEGORY_DB)
                .build();
    }

    public static CarDto getFirstCarFromDb() {
        return CarDto.builder()
                .id(1L)
                .licenseNumber(LICENSE_NUMBER_DB)
                .color(COLOR_DB)
                .seats(SEATS_DB)
                .brand(BRAND_DB)
                .model(MODEL_DB)
                .category(CATEGORY_DB)
                .build();
    }

    public static CarDto getSecondCarFromDb() {
        return CarDto.builder()
                .id(2L)
                .licenseNumber(LICENSE_NUMBER_DB_2)
                .color(COLOR_DB_2)
                .seats(SEATS_DB)
                .brand(BRAND_DB_2)
                .model(MODEL_DB_2)
                .category(CATEGORY_DB)
                .build();
    }

    public static List<CarDto> getCarDtoListFromDb() {
        return List.of(getFirstCarFromDb(), getSecondCarFromDb());
    }

    public static PaginationDto<CarDto> getPaginationDto() {
        return new PaginationDto<CarDto>(
                getCarDtoList(),
                PAGE_NUMBER,
                PAGE_SIZE,
                TOTAL_ELEMENTS,
                TOTAL_PAGES
        );
    }

    public static PaginationDto<CarDto> getPaginationDtoDb() {
        return new PaginationDto<CarDto>(
                getCarDtoListFromDb(),
                PAGE_NUMBER,
                PAGE_SIZE,
                TOTAL_ELEMENTS,
                TOTAL_PAGES
        );
    }

    public static PageRequest getPageRequest() {
        return PageRequest.of(PAGE_NUMBER, PAGE_SIZE, Sort.by(Sort.Direction.ASC, SORT_FIELD));
    }

    public static Page<Car> getPage() {
        return new PageImpl<>(getCarList(), getPageRequest(), TOTAL_ELEMENTS);
    }

    private CarTestConstants() {}
}