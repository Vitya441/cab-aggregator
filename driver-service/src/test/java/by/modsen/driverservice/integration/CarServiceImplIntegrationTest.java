package by.modsen.driverservice.integration;

import by.modsen.driverservice.config.BaseIntegrationTestConfig;
import by.modsen.driverservice.dto.request.CarCreateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.repository.CarRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static by.modsen.driverservice.util.CarTestConstants.CAR_ID_DB;
import static by.modsen.driverservice.util.CarTestConstants.CAR_PATH;
import static by.modsen.driverservice.util.CarTestConstants.getCarCreateDto;
import static by.modsen.driverservice.util.CarTestConstants.getFirstCarFromDb;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:sql/delete.sql",
        "classpath:sql/insert.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CarServiceImplIntegrationTest extends BaseIntegrationTestConfig {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private CarRepository carRepository;

    private String BASE_URL;

    @BeforeEach
    void setUp() {
        BASE_URL = "http://localhost:" + localServerPort + CAR_PATH;
    }

    @Test
    void getAllCars_shouldReturnCarsPage() {
        RestAssured.given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", hasSize(2));
    }

    @Test
    void getCarById_shouldReturnCar() {
        CarDto expected = getFirstCarFromDb();

        CarDto actual = RestAssured.given()
                .when()
                .get(BASE_URL + "/" + CAR_ID_DB)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CarDto.class);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void createCar_shouldReturnCreatedCar() {
        CarCreateDto createDto = getCarCreateDto();

        CarDto actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CarDto.class);

        assertEquals(createDto.licenseNumber(), actual.licenseNumber());
    }

    @Test
    void updateCar_shouldReturnUpdatedCar() {
        CarCreateDto updateDto = getCarCreateDto();
        CarDto actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put(BASE_URL + "/" + CAR_ID_DB)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CarDto.class);

        assertEquals(updateDto.licenseNumber(), actual.licenseNumber());
    }

    @Test
    void deleteCar() {
        RestAssured.given()
                .when()
                .delete(BASE_URL + "/" + CAR_ID_DB)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Assertions.assertFalse(carRepository.existsById(CAR_ID_DB));
    }
}