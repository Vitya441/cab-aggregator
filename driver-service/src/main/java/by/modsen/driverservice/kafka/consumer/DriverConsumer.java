package by.modsen.driverservice.kafka.consumer;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.kafka.NewUserDto;
import by.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DriverConsumer {

    private final DriverService driverService;

    @KafkaListener(
            topics = "${app.kafka.consumer.topic}",
            groupId = "${app.kafka.consumer.group-id}",
            containerFactory = "driverKafkaListenerContainerFactory"
    )
    public void handleDriverCreated(NewUserDto newUserDto) {
        log.info("Handling driver created. Started. Driver name = {}", newUserDto.firstName());
        driverService.create(
                DriverCreateDto.builder()
                        .firstName(newUserDto.firstName())
                        .lastName(newUserDto.lastName())
                        .build()
        );
    }
}
