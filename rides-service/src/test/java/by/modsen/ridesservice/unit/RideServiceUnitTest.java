package by.modsen.ridesservice.unit;

import by.modsen.commonmodule.dto.RequestedRideEvent;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.client.DriverClient;
import by.modsen.ridesservice.client.PassengerClient;
import by.modsen.ridesservice.client.PaymentClient;
import by.modsen.ridesservice.client.PriceClient;
import by.modsen.ridesservice.client.RatingClient;
import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import by.modsen.ridesservice.dto.response.PaginationDto;
import by.modsen.ridesservice.dto.response.PassengerDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.kafka.producer.DriverStatusProducer;
import by.modsen.ridesservice.kafka.producer.RequestedRidesProducer;
import by.modsen.ridesservice.mapper.RideMapper;
import by.modsen.ridesservice.mapper.RideMapperImpl;
import by.modsen.ridesservice.repository.RideRepository;
import by.modsen.ridesservice.service.impl.RideServiceImpl;
import by.modsen.ridesservice.util.RideStatusConstants;
import by.modsen.ridesservice.util.RideValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static by.modsen.ridesservice.util.RideTestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RideServiceUnitTest {

    @InjectMocks
    private RideServiceImpl rideService;

    @Mock
    private RideRepository rideRepository;

    @Spy
    private RideMapper rideMapper = new RideMapperImpl();

    @Mock
    private PassengerClient passengerClient;

    @Mock
    private DriverClient driverClient;

    @Mock
    private PriceClient priceClient;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private RatingClient ratingClient;

    @Mock
    private RideValidator rideValidator;

    @Mock
    private RequestedRidesProducer ridesProducer;

    @Mock
    private DriverStatusProducer driverStatusProducer;

    @Test
    void requestRide() {
        RideRequest rideRequest = getRideRequest();
        Ride ride = getRideWithoutId();
        Ride savedRide = getRide();
        PassengerDto passengerDto = getPassengerDto();
        RequestedRideEvent rideEvent = new RequestedRideEvent(RIDE_ID, PICKUP_ADDRESS);
        ride.setEstimatedCost(ESTIMATED_COST.divide(BigDecimal.valueOf(100)));

        when(passengerClient.getPassengerById(PASSENGER_ID))
                .thenReturn(passengerDto);
        when(rideRepository.existsByPassengerIdAndStatusNot(PASSENGER_ID, RideStatus.COMPLETED))
                .thenReturn(false);
        when(priceClient.calculatePriceForRide(rideRequest))
                .thenReturn(ESTIMATED_COST);
        when(rideRepository.save(ride))
                .thenReturn(savedRide);
        doNothing()
                .when(ridesProducer).sendEvent(rideEvent);

        rideService.requestRide(rideRequest);

        verify(passengerClient).getPassengerById(PASSENGER_ID);
        verify(rideRepository).existsByPassengerIdAndStatusNot(PASSENGER_ID, RideStatus.COMPLETED);
        verify(priceClient).calculatePriceForRide(rideRequest);
        verify(rideRepository).save(ride);
        verify(ridesProducer).sendEvent(rideEvent);
    }

    @Test
    void assignDriverToRide() {
        Ride ride = getRide();
        Ride rideWithDriver = getRideWithDriver();

        when(rideRepository.findById(RIDE_ID))
                .thenReturn(Optional.of(ride));
        when(rideRepository.save(rideWithDriver))
                .thenReturn(rideWithDriver);

        rideService.assignDriverToRide(DRIVER_ID, RIDE_ID);

        verify(rideRepository).findById(RIDE_ID);
        verify(rideRepository).save(rideWithDriver);
    }

    @Test
    void getActiveRide() {
        PassengerDto passengerDto = getPassengerDto();
        DriverWithCarDto driverDto = getDriverWithCarDto();
        Ride ride = getRide();

        when(passengerClient.getPassengerById(PASSENGER_ID))
                .thenReturn(passengerDto);
        when(rideRepository.findByPassengerIdAndStatusIn(passengerDto.id(), RideStatusConstants.ACTIVE_STATUSES))
                .thenReturn(Optional.of(ride));
        when(driverClient.getByIdWithCar(ride.getDriverId()))
                .thenReturn(driverDto);

        RideWithDriverResponse actualRide = rideService.getActiveRide(PASSENGER_ID);

        assertEquals(driverDto, actualRide.driver());
    }

    @Test
    void confirmRide() {
        Ride ride = getRide();
        Ride acceptedRide = getAcceptedRide();

        when(rideRepository.findById(RIDE_ID))
                .thenReturn(Optional.of(ride));
        doNothing()
                .when(rideValidator).validateConfirmation(ride, ride.getDriverId());
        when(rideRepository.save(acceptedRide))
                .thenReturn(acceptedRide);

        rideService.confirmRide(RIDE_ID, DRIVER_ID);

        verify(rideRepository).findById(RIDE_ID);
        verify(rideValidator).validateConfirmation(ride, ride.getDriverId());
        verify(rideRepository).save(acceptedRide);
    }

    @Test
    void getHistoryByPassengerId() {
        int page = 0;
        int size = 10;
        String sortField = "id";
        PassengerDto passengerDto = getPassengerDto();
        Ride ride1 = getRide();
        Ride ride2 = getSecondRide();
        Page<Ride> ridesPage = new PageImpl<>(List.of(ride1, ride2), PageRequest.of(page, size), 2);
        RideResponse rideResponse1 = getRideResponse();
        RideResponse rideResponse2 = getSecondRideResponse();

        PaginationDto<RideResponse> expectedPagination = new PaginationDto<>(
                List.of(rideResponse1, rideResponse2),
                ridesPage.getNumber(),
                ridesPage.getSize(),
                ridesPage.getTotalElements(),
                ridesPage.getTotalPages()
        );

        when(passengerClient.getPassengerById(PASSENGER_ID)).thenReturn(passengerDto);
        when(rideRepository.findAllByPassengerIdAndStatus(passengerDto.id(), RideStatus.COMPLETED, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField))))
                .thenReturn(ridesPage);

        PaginationDto<RideResponse> result = rideService.getHistoryByPassengerId(PASSENGER_ID, page, size, sortField);
        assertEquals(expectedPagination.content(), result.content());
        verify(passengerClient).getPassengerById(PASSENGER_ID);
        verify(rideRepository).findAllByPassengerIdAndStatus(passengerDto.id(), RideStatus.COMPLETED, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField)));
    }

    @Test
    void getHistoryByDriverId() {
        int page = 0;
        int size = 10;
        String sortField = "id";
        PassengerDto passengerDto = getPassengerDto();
        Ride ride1 = getRide();
        Ride ride2 = getSecondRide();
        Page<Ride> ridesPage = new PageImpl<>(List.of(ride1, ride2), PageRequest.of(page, size), 2);
        RideResponse rideResponse1 = getRideResponse();
        RideResponse rideResponse2 = getSecondRideResponse();

        PaginationDto<RideResponse> expectedPagination = new PaginationDto<>(
                List.of(rideResponse1, rideResponse2),
                ridesPage.getNumber(),
                ridesPage.getSize(),
                ridesPage.getTotalElements(),
                ridesPage.getTotalPages()
        );

        when(passengerClient.getPassengerById(DRIVER_ID)).thenReturn(passengerDto);
        when(rideRepository.findAllByPassengerIdAndStatus(passengerDto.id(), RideStatus.COMPLETED, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField))))
                .thenReturn(ridesPage);

        PaginationDto<RideResponse> result = rideService.getHistoryByPassengerId(DRIVER_ID, page, size, sortField);
        assertEquals(expectedPagination.content(), result.content());
        verify(passengerClient).getPassengerById(DRIVER_ID);
        verify(rideRepository).findAllByPassengerIdAndStatus(passengerDto.id(), RideStatus.COMPLETED, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField)));
    }
}