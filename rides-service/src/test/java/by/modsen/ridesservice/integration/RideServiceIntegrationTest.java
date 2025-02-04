package by.modsen.ridesservice.integration;

import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.config.RideIntegrationConfig;
import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.repository.RideRepository;
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

import java.util.Optional;

import static by.modsen.ridesservice.util.RideTestConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:sql/delete.sql",
        "classpath:sql/insert.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RideServiceIntegrationTest extends RideIntegrationConfig {

    @LocalServerPort
    private int localServerPort;

    private String BASE_URL;

    @Autowired
    private RideRepository rideRepository;

    @BeforeEach
    public void setUp() {
        BASE_URL = "http://localhost:" + localServerPort + RIDE_PATH;
    }

    @Test
    void requestRide() {
        RideRequest rideRequest = getRideRequest();

        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(PASSENGER_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                                {
                                    "id": 1,
                                    "firstName": "Viktor"
                                }
                                """)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(PRICE_PATH))
                .willReturn(WireMock.aResponse()
                        .withBody(String.valueOf(5))
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void getActiveRide() {
        Ride expected = getThirdRideDb();

        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(PASSENGER_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                                {
                                    "id": 103,
                                    "firstName": "Viktor",
                                    "lastName": "Maksimov"
                                }
                                """)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );

        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(DRIVER_PATH + "/with-car/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                                {
                                    "id": 204,
                                    "car": {
                                        "id": 1,
                                        "brand": "Audi"
                                    },
                                    "firstName": "John",
                                    "lastName": "Doe"
                                }
                                """)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );


        RideWithDriverResponse actual = RestAssured.given()
                .when()
                .get(BASE_URL + "/current?passengerId=" + 101)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RideWithDriverResponse.class);

        assertEquals(expected.getPickupAddress(), actual.pickupAddress());
    }

    @Test
    void confirmRide() {
        RestAssured.given()
                .when()
                .put(BASE_URL + "/2/confirm?driverId=203")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void rejectRide() {
        RestAssured.given()
                .when()
                .put(BASE_URL + "/2/reject?driverId=203")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Optional<Ride> ride = rideRepository.findById(2L);
        assertEquals(RideStatus.REQUESTED, ride.get().getStatus());
    }

    @Test
    void startRide() {
        RestAssured.given()
                .when()
                .put(BASE_URL + "/3/start?driverId=204")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Optional<Ride> ride = rideRepository.findById(3L);
        assertEquals(RideStatus.IN_PROGRESS, ride.get().getStatus());
    }

    @Test
    void endRide() {

        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(PASSENGER_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                   "id": 1,
                                   "customerId": "cus_123",
                                   "firstName": "Viktor",
                                   "lastName": "Maksimov"
                                }
                                """)
                )
        );

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(PAYMENT_CUSTOMERS_PATH))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                )
        );

        RestAssured.given()
                .when()
                .put(BASE_URL + "/1/end?driverId=202")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Optional<Ride> ride = rideRepository.findById(1L);
        assertEquals(RideStatus.COMPLETED, ride.get().getStatus());
    }

    @Test
    void getHistoryByPassengerId() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(PASSENGER_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                   "id": 101,
                                   "firstName": "Viktor",
                                   "lastName": "Maksimov"
                                }
                                """)
                )
        );

        RestAssured.given()
                .when()
                .get(BASE_URL + "/passenger/history/101")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", hasSize(2));
    }

    @Test
    void getHistoryByDriverId() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlMatching(DRIVER_PATH + "/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("""
                                {
                                   "id": 203,
                                   "firstName": "John",
                                   "lastName": "Doe"
                                }
                                """)
                )
        );

        RestAssured.given()
                .when()
                .get(BASE_URL + "/driver/history/203")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", hasSize(1));
    }

    @Test
    void rateDriver() {
        wireMockServer.stubFor(WireMock.put(WireMock.urlMatching(RATING_DRIVERS_PATH + "/\\d+\\?rating=\\d+(\\.\\d+)?"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                )
        );

        RestAssured.given()
                .when()
                .put(BASE_URL + "/4/rate-driver?passengerId=101&rating=5")
                .then()
                .statusCode(HttpStatus.OK.value());

        Optional<Ride> ride = rideRepository.findById(4L);
        assertTrue(ride.get().isRatedByPassenger());
    }
    @Test
    void ratePassenger() {
        wireMockServer.stubFor(WireMock.put(WireMock.urlMatching(RATING_PASSENGERS_PATH + "/\\d+\\?rating=\\d+(\\.\\d+)?"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                )
        );

        RestAssured.given()
                .when()
                .put(BASE_URL + "/4/rate-passenger?driverId=203&rating=5")
                .then()
                .statusCode(HttpStatus.OK.value());

        Optional<Ride> ride = rideRepository.findById(4L);
        assertTrue(ride.get().isRatedByDriver());
    }
}