package by.modsen.passengerservice.integration;

import by.modsen.passengerservice.config.DatabaseContainerConfig;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.repository.PassengerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static by.modsen.passengerservice.util.PassengerTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@AutoConfigureMockMvc
@Sql(scripts = {
        "classpath:sql/delete-passengers.sql",
        "classpath:sql/insert-passengers.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PassengerIntegrationTest extends DatabaseContainerConfig {

    @Autowired
    private MockMvc mockMvc;

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

    private final String BASE_URL =  "http://localhost:" + port + PASSENGER_PATH;

    @Test
    void getAllPassengers() throws Exception {

        System.out.println("SERVER PORT = " + port);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(8))
                .andReturn();
    }

    @Test
    void getPassengerById_shouldReturnFirstPassenger() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + PASSENGER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void deletePassengerById_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + PASSENGER_ID))
                .andExpect(status().isNoContent());

        Optional<Passenger> passenger = repository.findById(PASSENGER_ID);
        assertFalse(passenger.isPresent());
        assertFalse(repository.existsById(PASSENGER_ID));
    }

    @Test
    void updatePassengerById_shouldReturnUpdatedPassenger() throws Exception {
        PassengerUpdateDto updateDto = getPassengerUpdateDto();
        String jsonPassenger = asJsonString(updateDto);
        mockMvc.perform(put(BASE_URL + "/" + PASSENGER_ID)
                        .content(jsonPassenger)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PASSENGER_ID))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.phone").value(PHONE));
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

        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Viktor"))
                .andExpect(jsonPath("$.lastName").value("Maksimov"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}