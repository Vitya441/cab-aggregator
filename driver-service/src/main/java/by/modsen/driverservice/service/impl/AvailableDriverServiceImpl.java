package by.modsen.driverservice.service.impl;

import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.entity.Driver;
import by.modsen.driverservice.entity.enums.DriverStatus;
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

    /**
     * Если нет водителя со статусом AVAILABLE, то делаем RETRY 5 раз
     */
    @Override
    public DriverDto getAvailableDriver(String pickupLocation) {
         for (int i = 0; i < 5; ++i) {
             Driver firstDriver = driverRepository.findFirstByStatus(DriverStatus.AVAILABLE);
             if (firstDriver != null) {
                 firstDriver.setStatus(DriverStatus.BUSY);
                 driverRepository.save(firstDriver);
                 return driverMapper.toDto(firstDriver);
             }
             log.warn("No available drivers found. Retrying in 2 seconds...");
             try {
                 Thread.sleep(2000);
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
             }
         }
         throw new RuntimeException("No available drivers after multiple retries");
    }
}