package by.modsen.ridesservice.kafka.consumer;

import by.modsen.commonmodule.dto.AvailableDriverEvent;
import by.modsen.commonmodule.dto.DriverNotificationEvent;
import by.modsen.ridesservice.kafka.producer.DriverNotificationProducer;
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
    private final DriverNotificationProducer notificationProducer;

    @KafkaListener(
            topics = "${app.kafka.consumer.topic}",
            containerFactory = "availableDriversKafkaListenerContainerFactory"
    )
    public void listenAvailableDrivers(AvailableDriverEvent driverEvent) {
        log.info("Received driver for ride {}", driverEvent);
        rideService.assignDriverToRide(driverEvent.driverId(), driverEvent.rideId());
        sendNotificationToDriver(driverEvent);
    }

    private void sendNotificationToDriver(AvailableDriverEvent availableDriverEvent) {
        DriverNotificationEvent notificationEvent = DriverNotificationEvent.builder()
                .driverId(availableDriverEvent.driverId())
                .rideId(availableDriverEvent.rideId())
                .message("New available ride for you!")
                .build();

        notificationProducer.sendNotification(notificationEvent);
    }
}