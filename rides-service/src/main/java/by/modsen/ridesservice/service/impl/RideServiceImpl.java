package by.modsen.ridesservice.service.impl;

import by.modsen.commonmodule.dto.DriverStatusEvent;
import by.modsen.commonmodule.dto.RequestedRideEvent;
import by.modsen.commonmodule.enumeration.DriverStatus;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.dto.request.CustomerChargeRequest;
import by.modsen.ridesservice.dto.request.RideRequest;
import by.modsen.ridesservice.dto.response.DriverDto;
import by.modsen.ridesservice.dto.response.DriverWithCarDto;
import by.modsen.ridesservice.dto.response.PaginationDto;
import by.modsen.ridesservice.dto.response.PassengerDto;
import by.modsen.ridesservice.dto.response.RideResponse;
import by.modsen.ridesservice.dto.response.RideWithDriverResponse;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.ActiveRideExistsException;
import by.modsen.ridesservice.exception.RideNotFoundException;
import by.modsen.ridesservice.kafka.producer.DriverStatusProducer;
import by.modsen.ridesservice.kafka.producer.RequestedRidesProducer;
import by.modsen.ridesservice.mapper.RideMapper;
import by.modsen.ridesservice.repository.RideRepository;
import by.modsen.ridesservice.service.DriverService;
import by.modsen.ridesservice.service.PassengerService;
import by.modsen.ridesservice.service.PaymentService;
import by.modsen.ridesservice.service.PriceService;
import by.modsen.ridesservice.service.RatingService;
import by.modsen.ridesservice.service.RideService;
import by.modsen.ridesservice.util.ExceptionMessageConstants;
import by.modsen.ridesservice.util.RideStatusConstants;
import by.modsen.ridesservice.util.RideValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PassengerService passengerService;
    private final DriverService driverService;
    private final PriceService priceService;
    private final PaymentService paymentService;
    private final RatingService ratingService;
    private final RideValidator rideValidator;
    private final RequestedRidesProducer ridesProducer;
    private final DriverStatusProducer driverStatusProducer;

    @Override
    public void requestRide(RideRequest rideRequest) {
        PassengerDto passengerDto = passengerService.getPassengerById(rideRequest.passengerId());
        if (rideRepository.existsByPassengerIdAndStatusNot(passengerDto.id(), RideStatus.COMPLETED)) {
            throw new ActiveRideExistsException(ExceptionMessageConstants.ACTIVE_RIDE_EXISTS);
        }
        Ride ride = rideMapper.toEntity(rideRequest);
        BigDecimal cost = priceService.calculatePriceForRide(rideRequest).divide(BigDecimal.valueOf(100));
        ride.setEstimatedCost(cost);
        ride = rideRepository.save(ride);

        RequestedRideEvent rideEvent = RequestedRideEvent.builder()
                .rideId(ride.getId())
                .pickupAddress(ride.getPickupAddress())
                .build();

        ridesProducer.sendEvent(rideEvent);
        
    }

    @Override
    public void assignDriverToRide(Long driverId, Long rideId) {
        Ride ride = getRideByIdOrThrow(rideId);
        ride.setDriverId(driverId);

        rideRepository.save(ride);
    }

    @Override
    public RideWithDriverResponse getActiveRide(Long passengerId) {
        PassengerDto passengerDto = passengerService.getPassengerById(passengerId);
        Ride ride = rideRepository
                .findByPassengerIdAndStatusIn(passengerDto.id(), RideStatusConstants.ACTIVE_STATUSES)
                .orElseThrow(() -> new RideNotFoundException(ExceptionMessageConstants.NO_ACTIVE_RIDE_FOUND));

        Long driverId = ride.getDriverId();
        DriverWithCarDto driverDto = driverService.getByIdWithCar(driverId);

        return rideMapper.toDtoWithDriver(ride, driverDto);
    }

    @Override
    public void confirmRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        rideValidator.validateConfirmation(ride, driverId);
        ride.setStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);
    }

    @Override
    public void rejectRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        rideValidator.validateRejection(ride, driverId);
        ride.setDriverId(null);
        rideRepository.save(ride);

        DriverStatusEvent statusEvent = DriverStatusEvent.builder()
                .rideId(rideId)
                .driverId(driverId)
                .driverStatus(DriverStatus.AVAILABLE)
                .build();

        driverStatusProducer.sendEvent(statusEvent);

        RequestedRideEvent rideEvent = RequestedRideEvent.builder()
                .rideId(rideId)
                .build();

        ridesProducer.sendEvent(rideEvent);
    }

    @Override
    public void startRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        rideValidator.validateStarting(ride, driverId);
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    @Override
    public void endRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        rideValidator.validateEnding(ride, driverId);

        PassengerDto passenger = passengerService.getPassengerById(ride.getPassengerId());
        Long amount = ride.getEstimatedCost().longValue();
        CustomerChargeRequest chargeRequest = CustomerChargeRequest.builder()
                .customerId(passenger.customerId())
                .currency("USD")
                .amount(amount)
                .build();

        paymentService.chargeFromCustomer(chargeRequest);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        rideRepository.save(ride);

        DriverStatusEvent statusEvent = DriverStatusEvent.builder()
                .driverId(driverId)
                .driverStatus(DriverStatus.AVAILABLE)
                .build();

        driverStatusProducer.sendEvent(statusEvent);
    }

    @Override
    public PaginationDto<RideResponse> getHistoryByPassengerId(Long passengerId, int page, int size, String sortField) {
        PassengerDto passengerDto = passengerService.getPassengerById(passengerId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField));

        Page<Ride> ridesPage = rideRepository.findAllByPassengerIdAndStatus(passengerDto.id(), RideStatus.COMPLETED, pageRequest);
        List<RideResponse> data = ridesPage.getContent().stream()
                .map(rideMapper::toDto)
                .toList();

        return new PaginationDto<>(
                data,
                ridesPage.getNumber(),
                ridesPage.getSize(),
                ridesPage.getTotalElements(),
                ridesPage.getTotalPages()
        );
    }

    @Override
    public PaginationDto<RideResponse> getHistoryByDriverId(Long driverId, int page, int size, String sortField) {
        DriverDto driverDto =driverService.getDriverById(driverId);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortField));

        Page<Ride> ridesPage = rideRepository.findAllByDriverIdAndStatus(driverDto.id(), RideStatus.COMPLETED, pageRequest);
        List<RideResponse> data = ridesPage.getContent().stream()
                .map(rideMapper::toDto)
                .toList();

        return new PaginationDto<>(
                data,
                ridesPage.getNumber(),
                ridesPage.getSize(),
                ridesPage.getTotalElements(),
                ridesPage.getTotalPages()
        );
    }

    @Override
    public void rateDriver(Long rideId, Long passengerId, double rating) {
        Ride ride = getRideByIdOrThrow(rideId);
        Long driverId = ride.getDriverId();
        rideValidator.validateDriverRating(ride, passengerId);
        ratingService.updateDriverRating(driverId, rating);
        ride.setRatedByPassenger(true);

        rideRepository.save(ride);
    }

    @Override
    public void ratePassenger(Long rideId, Long driverId, double rating) {
        Ride ride = getRideByIdOrThrow(rideId);
        Long passengerId = ride.getPassengerId();
        rideValidator.validatePassengerRating(ride, driverId);
        ratingService.updatePassengerRating(passengerId, rating);
        ride.setRatedByDriver(true);

        rideRepository.save(ride);
    }

    private Ride getRideByIdOrThrow(Long rideId) {
        return rideRepository
                .findById(rideId)
                .orElseThrow(() -> new RideNotFoundException(ExceptionMessageConstants.RIDE_NOT_FOUND));
    }
}