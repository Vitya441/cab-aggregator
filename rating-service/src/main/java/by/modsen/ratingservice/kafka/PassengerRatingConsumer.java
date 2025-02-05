package by.modsen.ratingservice.kafka;

import by.modsen.ratingservice.service.PassengerRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PassengerRatingConsumer {

    private final PassengerRatingService passengerRatingService;

    @KafkaListener(
            topics = "${app.kafka.passenger-rating-consumer.topic}",
            groupId = "${app.kafka.passenger-rating-consumer.groupId}",
            containerFactory = "passengerRatingKafkaListenerContainerFactory"
    )
    public void consume(Long passengerId) {
        log.info("Получено событие для создания рейтинга пассажира с ID: {}", passengerId);
        passengerRatingService.createRatingRecord(passengerId);
    }
}