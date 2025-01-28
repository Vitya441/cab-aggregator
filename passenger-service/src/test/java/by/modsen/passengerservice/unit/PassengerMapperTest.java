package by.modsen.passengerservice.unit;

import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.mapper.PassengerMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static by.modsen.passengerservice.util.PassengerTestConstants.getPassengerCreateDto;
import static by.modsen.passengerservice.util.PassengerTestConstants.getPassengerWithPhone;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PassengerMapperTest {

    private PassengerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PassengerMapperImpl();
    }

    @Test
    void shouldMapPassengerDtoToPassengerEntity() {
        PassengerCreateDto passengerCreateDto = getPassengerCreateDto();

        Passenger passenger = mapper.toPassenger(passengerCreateDto);

        assertEquals(passengerCreateDto.firstName(), passenger.getFirstName());
        assertEquals(passengerCreateDto.lastName(), passenger.getLastName());
    }

    @Test
    void shouldMapPassengerEntityToPassengerDto() {
        Passenger passenger = getPassengerWithPhone();

        PassengerDto passengerDto = mapper.toPassengerDto(passenger);

        assertEquals(passengerDto.firstName(), passenger.getFirstName());
        assertEquals(passengerDto.lastName(), passenger.getLastName());
        assertEquals(passengerDto.phone(), passenger.getPhone());
    }
}