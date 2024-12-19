package by.modsen.driverservice.kafka.producer;

import by.modsen.commonmodule.dto.AvailableDriverEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableDriverProducer {

    @Value("${app.kafka.producer.topic}")
    private String TOPIC_NAME;

    private final KafkaTemplate<String, AvailableDriverEvent> kafkaTemplate;

    public void send(AvailableDriverEvent availableDriver) {
        kafkaTemplate.send(TOPIC_NAME, availableDriver);
        log.info("-----Message sent to available-drivers-topic-----");
    }

}