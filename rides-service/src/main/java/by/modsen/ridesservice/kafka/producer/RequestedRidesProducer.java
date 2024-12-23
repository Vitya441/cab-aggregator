package by.modsen.ridesservice.kafka.producer;

import by.modsen.commonmodule.dto.RequestedRideEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestedRidesProducer {

    public static final String TOPIC_NAME = "requested-rides-topic";

    private final KafkaTemplate<String, RequestedRideEvent> kafkaTemplate;

    public void sendEvent(RequestedRideEvent rideEvent) {
        kafkaTemplate.send(TOPIC_NAME, rideEvent);
        log.info("Send message: {}", rideEvent);
    }
}