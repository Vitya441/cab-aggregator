package by.modsen.driverservice.kafka;

import by.modsen.driverservice.dto.request.DriverCreateDto;
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
            topics = "#{@environment.getProperty('app.kafka.consumer.topic')}",
            groupId = "#{@environment.getProperty('app.kafka.consumer.group-id')}"
    )
    public void handleDriverCreated(NewUserDto newUserDto) {
        log.info("Driver received: {}", newUserDto);
        driverService.create(
                DriverCreateDto.builder()
                        .firstName(newUserDto.firstName())
                        .lastName(newUserDto.lastName())
                        .build()
        );
    }
}