package by.modsen.driverservice.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RatingProducer {

    private final String TOPIC_NAME;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public RatingProducer(
            @Value("${app.kafka.rating-producer.topic}")
            String TOPIC_NAME,
            KafkaTemplate<String, Long> kafkaTemplate
    ) {
        this.TOPIC_NAME = TOPIC_NAME;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Long passengerId) {
        log.info("Sending rating to topic, passengerId = {}", passengerId);
        kafkaTemplate.send(TOPIC_NAME, passengerId);
    }
}