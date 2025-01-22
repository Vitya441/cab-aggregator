package by.modsen.ridesservice.kafka.producer;

import by.modsen.commonmodule.dto.DriverNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DriverNotificationProducer {

    private final String NOTIFICATION_TOPIC;
    private final KafkaTemplate<String, DriverNotificationEvent> kafkaTemplate;

    public DriverNotificationProducer(
            @Value("${app.kafka.driver-notifications-producer.topic}")
            String notificationTopic,
            KafkaTemplate<String, DriverNotificationEvent> kafkaTemplate)
    {
        NOTIFICATION_TOPIC = notificationTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(DriverNotificationEvent event) {
        kafkaTemplate.send(NOTIFICATION_TOPIC, event);
        log.info("Notification sent to driver with driver_id = {}", event.driverId());
    }
}