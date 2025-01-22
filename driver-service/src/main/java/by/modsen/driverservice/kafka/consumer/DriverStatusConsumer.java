package by.modsen.driverservice.kafka.consumer;

import by.modsen.commonmodule.dto.DriverStatusEvent;
import by.modsen.driverservice.service.AvailableDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DriverStatusConsumer {

    private final AvailableDriverService availableDriverService;

    @KafkaListener(
            topics = "${app.kafka.driver-status-consumer.topic}",
            groupId = "${app.kafka.driver-status-consumer.group-id}",
            containerFactory = "driverStatusKafkaListenerContainerFactory"
    )
    public void listenStatusChanged(DriverStatusEvent event) {
        log.info("Driver status changed: {}", event.driverStatus());
        availableDriverService.changeDriverStatus(event.driverId(), event.driverStatus());
        if (event.rideId() != null) {
            availableDriverService.updateLastRejectedRide(event.driverId(), event.rideId());
        }
    }
}