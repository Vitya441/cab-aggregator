package by.modsen.driverservice.kafka.consumer;

import by.modsen.commonmodule.dto.AvailableDriverEvent;
import by.modsen.commonmodule.dto.RequestedRideEvent;
import by.modsen.driverservice.dto.response.DriverDto;
import by.modsen.driverservice.kafka.producer.AvailableDriverProducer;
import by.modsen.driverservice.service.AvailableDriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RidesConsumer {

    private final AvailableDriverProducer producer;
    private final AvailableDriverService availableDriverService;

    @KafkaListener(
            topics = "${app.kafka.consumer-2.topic}",
            groupId = "${app.kafka.consumer-2.group-id}",
            containerFactory = "ridesKafkaListenerContainerFactory"
    )
    public void listenRideRequest(RequestedRideEvent rideEvent) {
        log.info("Consumed -> {}", rideEvent.pickupAddress());
        DriverDto driverDto = availableDriverService.getAvailableDriver(rideEvent.pickupAddress());

        AvailableDriverEvent availableDriverEvent = AvailableDriverEvent.builder()
                .driverId(driverDto.id())
                .rideId(rideEvent.rideId())
                .build();

        producer.send(availableDriverEvent);
    }
}