package by.modsen.ratingservice.kafka;

import by.modsen.ratingservice.service.DriverRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DriverRatingConsumer {

    private final DriverRatingService driverRatingService;

    @KafkaListener(
            topics = "${app.kafka.driver-rating-consumer.topic}",
            groupId = "${app.kafka.driver-rating-consumer.groupId}",
            containerFactory = "passengerRatingKafkaListenerContainerFactory"
    )
    public void consume(Long driverId) {
        log.info("Получено событие для создания рейтинга водителя с ID: {}", driverId);
        driverRatingService.createRatingRecord(driverId);
    }
}