package by.modsen.passengerservice.unit;

import by.modsen.passengerservice.controller.PassengerController;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.service.impl.PassengerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static by.modsen.passengerservice.util.PassengerTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    @Mock   
    PassengerServiceImpl passengerService;

    @InjectMocks
    PassengerController passengerController;

    @Test
    void getAllPassengers_shouldReturnAllPassengers() {
        // given
        doReturn(getPaginatedResponse()).when(passengerService).getAll(0, 15);

        // when
        ResponseEntity<PaginationDto<PassengerDto>> response = passengerController.getAll(0, 15);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(getPassengerDtoList(), response.getBody().getData());
        assertEquals(2, response.getBody().getTotalItems());
    }

    @Test
    void getPassengerById_shouldReturnPassenger() {;
        // given
        doReturn(getPassengerDto()).when(passengerService).getById(PASSENGER_ID);

        // when
        ResponseEntity<PassengerDto> passengerResponse = passengerController.getById(PASSENGER_ID);

        // then
        assertNotNull(passengerResponse);
        assertEquals(HttpStatus.OK, passengerResponse.getStatusCode());
        assertEquals(getPassengerDto(), passengerResponse.getBody());
    }

    @Test
    void createPassenger_shouldReturnValidResponse_whenPayloadIsValid() {
        // given
        PassengerCreateDto createDto = getPassengerCreateDto();
        PassengerDto passengerDto = getPassengerDto();
        doReturn(passengerDto).when(passengerService).create(createDto);
        // when
        ResponseEntity<PassengerDto> responseEntity = passengerController.create(createDto);

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}