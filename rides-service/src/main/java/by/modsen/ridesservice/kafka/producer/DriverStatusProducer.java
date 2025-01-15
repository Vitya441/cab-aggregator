package by.modsen.ridesservice.kafka.producer;

import by.modsen.commonmodule.dto.DriverStatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DriverStatusProducer {

    private final String TOPIC_NAME;

    private final KafkaTemplate<String, DriverStatusEvent> kafkaTemplate;

    public DriverStatusProducer(
            KafkaTemplate<String, DriverStatusEvent> kafkaTemplate,
            @Value("${app.kafka.driver-status-producer.topic}")
            String TOPIC_NAME) {
        this.kafkaTemplate = kafkaTemplate;
        this.TOPIC_NAME = TOPIC_NAME;
    }

    public void sendEvent(DriverStatusEvent event) {
        kafkaTemplate.send(TOPIC_NAME, event);
        log.info("Driver status change event sent to topic {}", TOPIC_NAME);
    }
}