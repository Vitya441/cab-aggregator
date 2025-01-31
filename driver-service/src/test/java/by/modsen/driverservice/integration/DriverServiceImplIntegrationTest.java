package by.modsen.driverservice.integration;

import by.modsen.driverservice.config.DriverIntegrationTestConfig;
import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.dto.request.DriverUpdateDto;
import by.modsen.driverservice.dto.response.CarDto;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.entity.Car;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.repository.DriverRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static by.modsen.driverservice.util.CarTestConstants.getFirstCarEntityFromDb;
import static by.modsen.driverservice.util.CarTestConstants.getFirstCarFromDb;
import static by.modsen.driverservice.util.DriverTestConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:sql/delete.sql",
        "classpath:sql/insert.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DriverServiceImplIntegrationTest extends DriverIntegrationTestConfig {

    @LocalServerPort
    private int localServerPort;

    private String BASE_URL;

    @Autowired
    private DriverRepository driverRepository;

    @BeforeEach
    public void setUp() {
        BASE_URL = "http://localhost:" + localServerPort + DRIVER_PATH;
    }

    @Test
    void getDriverById() {
        DriverDto expected = getDriverFromDb();

        DriverDto actual = RestAssured.given()
                .when()
                .get(BASE_URL + "/" + DRIVER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverDto.class);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getAllDrivers() {
        RestAssured.given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", hasSize(2));
    }

    @Test
    void updateDriver() {
        DriverUpdateDto updateDto = getDriverUpdateDto();

        DriverDto actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put(BASE_URL + "/" + DRIVER_ID_NEW)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(DriverDto.class);

        assertEquals(updateDto.phone(), actual.phone());
    }

    @Test
    void deleteDriverById() {
        RestAssured.given()
                .when()
                .delete(BASE_URL + "/" + DRIVER_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        assertFalse(driverRepository.existsById(DRIVER_ID));
    }

    @Test
    void createDriver() {
        DriverDto expected = getWireMockDriver();
        DriverCreateDto createDto = getDriverCreateDto();

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(RATING_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.CREATED.value())));

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(PAYMENT_PATH))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                                {
                                    "customerId": "cus_123_xyz",
                                    "name": "Viktor",
                                    "balance": 1000
                                }
                                """)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );

        DriverDto actual = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(DriverDto.class);

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void assignCarToDriver() {
        DriverDto driver = getDriverFromDb();
        CarDto car = getFirstCarFromDb();

        RestAssured.given()
                .port(localServerPort)
                .when()
                .post(BASE_URL + "/" + driver.id() + "/assign-car?carId=" + car.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value()); // 204 No Content

        Driver updatedDriver = driverRepository
                .findById(driver.id())
                .orElseThrow();

        assertNotNull(updatedDriver.getCar());
        assertEquals(car.id(), updatedDriver.getCar().getId());
    }

    @Test
    void unAssignCarFromDriver() {
        Car car = getFirstCarEntityFromDb();
        Driver driver = getDriverEntityFromDb();
        driver.setCar(car);
        driverRepository.save(driver);

        RestAssured.given()
                .port(localServerPort)
                .when()
                .post(BASE_URL + "/" + driver.getId() + "/unassign-car")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value()); // 204 No Content

        Driver updatedDriver = driverRepository
                .findById(driver.getId())
                .orElseThrow();

        assertNull(updatedDriver.getCar());
    }
}