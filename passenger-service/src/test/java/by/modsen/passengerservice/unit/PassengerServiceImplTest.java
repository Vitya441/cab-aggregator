package by.modsen.passengerservice.unit;

import by.modsen.passengerservice.client.PaymentClient;
import by.modsen.passengerservice.client.RatingClient;
import by.modsen.passengerservice.dto.request.CustomerRequest;
import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.dto.request.PassengerUpdateDto;
import by.modsen.passengerservice.dto.response.CustomerResponse;
import by.modsen.passengerservice.dto.response.PaginationDto;
import by.modsen.passengerservice.dto.response.PassengerDto;
import by.modsen.passengerservice.entity.Passenger;
import by.modsen.passengerservice.exception.PassengerNotFoundException;
import by.modsen.passengerservice.mapper.PassengerMapper;
import by.modsen.passengerservice.mapper.PassengerMapperImpl;
import by.modsen.passengerservice.repository.PassengerRepository;
import by.modsen.passengerservice.service.impl.PassengerServiceImpl;
import by.modsen.passengerservice.utils.MessageUtils;
import by.modsen.passengerservice.utils.PassengerValidator;
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

import static by.modsen.passengerservice.util.PassengerTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private RatingClient ratingClient;

    @Spy
    private PassengerMapper passengerMapper = new PassengerMapperImpl();

    @Mock
    private PassengerValidator passengerValidator;

    @Test
    void createPassenger_shouldCreatePassenger() {
        PassengerCreateDto passengerCreateDto = getPassengerCreateDto();
        Passenger passenger = getPassenger();
        PassengerDto passengerDto = getPassengerDto();
        CustomerRequest customerRequest = getCustomerRequest();
        CustomerResponse customerResponse = getCustomerResponse();

        when(paymentClient.createCustomer(customerRequest))
                .thenReturn(customerResponse);
        doReturn(passenger)
                .when(passengerRepository).save(any(Passenger.class));
        doNothing().when(ratingClient).
                createPassengerRatingRecord(passenger.getId());

        PassengerDto responseDto = passengerService.create(passengerCreateDto);

        assertEquals(passengerDto.firstName(), responseDto.firstName());
        assertEquals(passengerDto.lastName(), responseDto.lastName());
        verify(ratingClient).createPassengerRatingRecord(passenger.getId());
        verify(passengerRepository).save(any(Passenger.class));
        verify(ratingClient).createPassengerRatingRecord(passenger.getId());
    }

    @Test
    void updatePassenger_shouldUpdatePassenger() {
        PassengerUpdateDto updateDto = getPassengerUpdateDto();
        Passenger currentPassenger = getPassenger();
        Passenger updatedPassenger = getUpdatedPassenger();
        PassengerDto updatedDto = getUpdatedPassengerDto();

        when(passengerRepository.findById(PASSENGER_ID))
                .thenReturn(Optional.of(currentPassenger));
        when(passengerRepository.save(currentPassenger))
                .thenReturn(updatedPassenger);

        PassengerDto updatedResponse = passengerService.update(PASSENGER_ID, updateDto);

        assertNotNull(updatedResponse);
        assertEquals(updatedDto.firstName(), updatedResponse.firstName());
        assertEquals(updatedDto.lastName(), updatedResponse.lastName());
        assertEquals(updatedDto.phone(), updatedResponse.phone());
        verify(passengerRepository).findById(PASSENGER_ID);
        verify(passengerRepository).save(currentPassenger);
    }

    @Test
    void updatePassenger_shouldThrowException_whenPassengerNotFound() {
        Long invalidId = 999L;
        PassengerUpdateDto passengerUpdateDto = getPassengerUpdateDto();

        when(passengerRepository.findById(invalidId))
                .thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> passengerService.update(invalidId, passengerUpdateDto));
        verify(passengerRepository).findById(invalidId);
    }

    @Test
    void findById_shouldFindPassenger_whenPassengerExists() {
        Long passengerId = PASSENGER_ID;
        Passenger passenger = getPassenger();

        when(passengerRepository.findById(passengerId))
                .thenReturn(Optional.of(passenger));

        PassengerDto passengerResponse = passengerService.getById(passengerId);

        assertNotNull(passengerResponse);
        assertEquals(passenger.getId(), passengerResponse.id());
        verify(passengerRepository).findById(passengerId);
    }

    @Test
    void findById_shouldThrowException_whenPassengerNotFound() {
        Long invalidId = 999L;

        when(passengerRepository.findById(invalidId))
                .thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> passengerService.getById(invalidId));
        verify(passengerRepository).findById(invalidId);
    }

    @Test
    void getAll_shouldReturnPaginationDto() {
        Page<Passenger> passengerPage = mock(Page.class);
        List<Passenger> passengerList = getPassengerList();
        List<PassengerDto> passengerDtos = getPassengerDtoList();

        when(passengerRepository.findAll(any(PageRequest.class)))
                .thenReturn(passengerPage);
        when(passengerPage.getContent())
                .thenReturn(passengerList);
        when(passengerPage.getTotalElements())
                .thenReturn((long) passengerList.size());

        PaginationDto<PassengerDto> paginationDto = passengerService.getAll(PAGE_NUMBER, PAGE_SIZE);

        assertNotNull(paginationDto);
        assertEquals(passengerDtos.size(), paginationDto.getTotalItems());
        assertEquals(passengerList.size(), passengerDtos.size());
        verify(passengerRepository).findAll(any(PageRequest.class));
        verify(passengerPage).getContent();
        verify(passengerPage).getTotalElements();
    }

    @Test
    void deletePassenger_shouldDeletePassenger_whenPassengerExists() {
        Passenger passenger = getPassenger();

        when(passengerRepository.findById(PASSENGER_ID))
                .thenReturn(Optional.of(passenger));
        doNothing().when(passengerRepository)
                .deleteById(PASSENGER_ID);

        passengerService.deleteById(PASSENGER_ID);

        verify(passengerRepository).findById(PASSENGER_ID);
        verify(passengerRepository).deleteById(PASSENGER_ID);
    }

    @Test
    void deletePassenger_shouldThrowException_whenPassengerNotFound() {
        Long passengerId = 999L;

        when(passengerRepository.findById(passengerId))
                .thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(
                PassengerNotFoundException.class,
                () -> passengerService.deleteById(passengerId)

        );
        assertEquals(MessageUtils.PASSENGER_NOT_FOUND_ERROR, exception.getMessage());
        verify(passengerRepository).findById(passengerId);
    }
}