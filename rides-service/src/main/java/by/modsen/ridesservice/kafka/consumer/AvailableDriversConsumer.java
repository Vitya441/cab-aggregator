package by.modsen.ridesservice.kafka.consumer;

import by.modsen.commonmodule.dto.AvailableDriverEvent;
import by.modsen.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableDriversConsumer {

    private final RideService rideService;

    @KafkaListener(
            topics = "${app.kafka.consumer.topic}",
            containerFactory = "availableDriversKafkaListenerContainerFactory"
    )
    public void listen(AvailableDriverEvent driverEvent) {
        log.info("Received driver for ride {}", driverEvent);
        rideService.assignDriverToRide(driverEvent.driverId(), driverEvent.rideId());
    }
}