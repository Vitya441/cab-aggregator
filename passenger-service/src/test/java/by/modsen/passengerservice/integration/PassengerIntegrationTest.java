package by.modsen.passengerservice.integration;

import by.modsen.passengerservice.config.DatabaseContainerConfig;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.repository.PassengerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import static by.modsen.passengerservice.util.PassengerTestConstants.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@Sql(scripts = {
        "classpath:sql/delete-passengers.sql",
        "classpath:sql/insert-passengers.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PassengerIntegrationTest extends DatabaseContainerConfig {

    @LocalServerPort
    private int port;

    @Autowired
    private PassengerRepository repository;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void wireMockProperties(DynamicPropertyRegistry registry) {
        registry.add("app.feign-clients.rating-service.url", wireMockServer::baseUrl);
        registry.add("app.feign-clients.payment-service.url", wireMockServer::baseUrl);
    }

    private String BASE_URL;

    @BeforeEach
    void setup() {
        BASE_URL = "http://localhost:" + port + PASSENGER_PATH;
    }

    @Test
    void getAllPassengers() {
        RestAssured.given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("data", hasSize(8));
    }

    @Test
    void getPassengerById_shouldReturnFirstPassenger() {
        RestAssured.given()
                .when()
                .get(BASE_URL + "/" + PASSENGER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"));
    }

    @Test
    void deletePassengerById_shouldReturnNoContent() throws Exception {
        RestAssured.given()
                .when()
                .delete(BASE_URL + "/" + PASSENGER_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Assertions.assertFalse(repository.existsById(PASSENGER_ID));
    }

    @Test
    void updatePassengerById_shouldReturnUpdatedPassenger() throws Exception {
        PassengerUpdateDto updateDto = getPassengerUpdateDto();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateDto)
                .when()
                .put(BASE_URL + "/" + PASSENGER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(1))
                .body("firstName", equalTo("John"))
                .body("phone", equalTo(PHONE));
    }

    @Test
    void createPassenger_shouldReturnCreatedPassenger() throws Exception {
        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(RATING_PATH +"/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                        {
                            "test": "123"
                        }""")
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                ));

        wireMockServer.stubFor(WireMock.post(WireMock.urlMatching(PAYMENT_PATH))
                .willReturn(WireMock.aResponse()
                        .withBody("""
                                {
                                    "customerId": "ABC_123_XYZ",
                                    "name": "Viktor",
                                    "balance": 1000
                                }
                                """)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                ));

        PassengerCreateDto createDto = getPassengerCreateDto();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createDto)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("firstName", equalTo(FIRST_NAME))
                .body("lastName", equalTo(LAST_NAME));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}