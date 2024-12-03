package by.modsen.passengerservice.kafka;

import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassengerConsumer {

    private final PassengerService passengerService;

    @KafkaListener(
            topics = "#{@environment.getProperty('app.kafka.consumer.topic')}",
            groupId = "#{@environment.getProperty('app.kafka.consumer.group-id')}"
    )
    public void handlePassengerCreated(NewUserDto newUserDto) {
        log.info("Handling passenger created. Started. Passenger name = {}", newUserDto.firstName());
        passengerService.create(
                PassengerCreateDto.builder()
                        .firstName(newUserDto.firstName())
                        .lastName(newUserDto.lastName())
                        .build()
        );

    }
}