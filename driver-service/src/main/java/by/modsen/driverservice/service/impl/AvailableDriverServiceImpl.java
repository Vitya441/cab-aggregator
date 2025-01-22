package by.modsen.driverservice.service.impl;

import by.modsen.commonmodule.enumeration.DriverStatus;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.mapper.DriverMapper;
import by.modsen.driverservice.repository.DriverRepository;
import by.modsen.driverservice.service.AvailableDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvailableDriverServiceImpl implements AvailableDriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverDto getAvailableDriver(Long rideId) {
        Driver driverForRide = driverRepository
                .findFirstByStatusAndRejectedRideIdNotOrRejectedRideIdIsNull(DriverStatus.AVAILABLE, rideId)
                .orElseThrow(() -> new RuntimeException("Driver for ride not found"));

        driverForRide.setStatus(DriverStatus.BUSY);
        driverForRide = driverRepository.save(driverForRide);

        return driverMapper.toDto(driverForRide);
    }


    @Override
    public void changeDriverStatus(Long driverId, DriverStatus driverStatus) {
        Driver driver = driverRepository
                .findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setStatus(driverStatus);

        driverRepository.save(driver);
    }

    @Override
   public void updateLastRejectedRide(Long driverId, Long rideId) {
       Driver driver = driverRepository
               .findById(driverId)
               .orElseThrow(() -> new RuntimeException("Driver not found"));

       driver.setRejectedRideId(rideId);
       driverRepository.save(driver);
   }
}