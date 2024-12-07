package by.modsen.ridesservice.service.impl;

import by.modsen.commonmodule.dto.DriverDto;
import by.modsen.commonmodule.dto.PassengerDto;
import by.modsen.commonmodule.dto.RideRequest;
import by.modsen.commonmodule.dto.RideResponse;
import by.modsen.commonmodule.enumeration.RideStatus;
import by.modsen.ridesservice.client.DriverClient;
import by.modsen.ridesservice.client.PassengerClient;
import by.modsen.ridesservice.entity.Ride;
import by.modsen.ridesservice.exception.ActiveRideExistsException;
import by.modsen.ridesservice.exception.NotFoundException;
import by.modsen.ridesservice.mapper.RideMapper;
import by.modsen.ridesservice.repository.RideRepository;
import by.modsen.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PassengerClient passengerClient;
    private final DriverClient driverClient;

    @Override
    public void requestRide(RideRequest rideRequest) {
        PassengerDto passengerDto = passengerClient.getPassengerById(rideRequest.passengerId());
        log.info("Passenger ID = {}", passengerDto.id());

        if (rideRepository.existsByPassengerIdAndStatusNot(passengerDto.id(), RideStatus.CANCELLED)) {
            throw new ActiveRideExistsException("You already have an active ride");
        }
        Ride ride = rideMapper.toEntity(rideRequest);
        rideRepository.save(ride);
    }

    @Override
    public RideResponse getCurrentRide(Long passengerId) {
        PassengerDto passengerDto = passengerClient.getPassengerById(passengerId);
        Ride ride = rideRepository
                .findByPassengerIdAndStatusNot(
                        passengerDto.id(),
                        RideStatus.CANCELLED)
                .orElseThrow(() -> new NotFoundException("You dont have an active ride"));

        return rideMapper.toDto(ride);
    }

    @Override
    public void confirmRide(Long rideId, Long driverId) {
        Ride ride = rideRepository
                .findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride with this id not found"));

        DriverDto driverDto = driverClient.getDriverById(driverId);

        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.CANCELLED) {
            throw new NotFoundException("Ride is not waiting for a driver");
        }

        ride.setDriverId(driverDto.id());
        ride.setStatus(RideStatus.ACCEPTED);
        rideRepository.save(ride);
    }

    @Override
    public void rejectRide(Long rideId, Long driverId) {
        Ride ride = rideRepository
                .findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride with this id not found"));

        DriverDto driverDto = driverClient.getDriverById(driverId);

        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }

}