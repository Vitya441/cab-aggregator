package by.modsen.ridesservice.service.impl;

import by.modsen.commonmodule.dto.DriverDto;
import by.modsen.commonmodule.dto.PassengerDto;
import by.modsen.commonmodule.dto.RideRequest;
import by.modsen.commonmodule.dto.RideResponse;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.client.DriverClient;
import by.modsen.ridesservice.client.PassengerClient;
import by.modsen.ridesservice.dto.PaginationDto;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.ActiveRideExistsException;
import by.modsen.ridesservice.exception.NotFoundException;
import by.modsen.ridesservice.mapper.RideMapper;
import by.modsen.ridesservice.repository.RideRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PassengerClient passengerClient;
    private final DriverClient driverClient;
    private final RideValidator rideValidator;

    @Override
    public void requestRide(RideRequest rideRequest) {
        PassengerDto passengerDto = passengerClient.getPassengerById(rideRequest.passengerId());
        if (rideRepository.existsByPassengerIdAndStatusNot(passengerDto.id(), RideStatus.COMPLETED)) {
            throw new ActiveRideExistsException(ExceptionMessageConstants.ACTIVE_RIDE_EXISTS);
        }
        Ride ride = rideMapper.toEntity(rideRequest);
        rideRepository.save(ride);
    }

    @Override
    public RideResponse getCurrentRide(Long passengerId) {
        PassengerDto passengerDto = passengerClient.getPassengerById(passengerId);
        Ride ride = rideRepository
                .findByPassengerIdAndStatusIn(passengerDto.id(), RideStatusConstants.ACTIVE_STATUSES)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageConstants.NO_ACTIVE_RIDE_FOUND));

        return rideMapper.toDto(ride);
    }

    @Override
    public PaginationDto<RideResponse> getHistoryByPassengerId(Long passengerId, int page, int size, String sortField) {
        PassengerDto passengerDto = passengerClient.getPassengerById(passengerId);
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
        DriverDto driverDto =driverClient.getDriverById(driverId);
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
    public void confirmRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        DriverDto driverDto = driverClient.getDriverById(driverId);
        rideValidator.validateConfirmation(ride);
        ride.setDriverId(driverDto.id());
        ride.setStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);
    }

    @Override
    public void rejectRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        DriverDto driverDto = driverClient.getDriverById(driverId);
        rideValidator.validateRejection(ride);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }

    @Override
    public void startRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        DriverDto driverDto = driverClient.getDriverById(driverId);
        rideValidator.validateStarting(driverDto, ride);
        ride.setStatus(RideStatus.IN_PROGRESS);
        rideRepository.save(ride);
    }

    @Override
    public void endRide(Long rideId, Long driverId) {
        Ride ride = getRideByIdOrThrow(rideId);
        DriverDto driverDto = driverClient.getDriverById(driverId);
        rideValidator.validateEnding(driverDto, ride);
        ride.setStatus(RideStatus.COMPLETED);
        rideRepository.save(ride);
    }

    private Ride getRideByIdOrThrow(Long rideId) {
        return rideRepository
                .findById(rideId)
                .orElseThrow(() -> new NotFoundException(ExceptionMessageConstants.RIDE_NOT_FOUND));
    }
}