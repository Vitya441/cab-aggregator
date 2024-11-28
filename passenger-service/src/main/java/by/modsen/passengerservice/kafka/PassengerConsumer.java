package by.modsen.passengerservice.kafka;

import by.modsen.passengerservice.dto.request.PassengerCreateDto;
import by.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassengerConsumer {

    private final PassengerService passengerService;

    @KafkaListener(
            topics = "passenger-topic",
            groupId = "passenger-group"
    )
    public void receiveUserFromKeycloakAndCreate(NewUserDto newUserDto) {
        System.out.println("Полученный пользователь (email): " + newUserDto);

        passengerService.create(
                PassengerCreateDto.builder()
                        .firstName(newUserDto.firstName())
                        .lastName(newUserDto.lastName())
                        .build()
        );

    }
}