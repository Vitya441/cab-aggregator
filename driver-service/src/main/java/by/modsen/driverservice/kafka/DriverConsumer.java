package by.modsen.driverservice.kafka;

import by.modsen.driverservice.dto.request.DriverCreateDto;
import by.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverConsumer {

    private final DriverService driverService;

    @KafkaListener(
            topics = "driver-topic",
            groupId = "driver-group"
    )
    public void listen(NewUserDto newUserDto) {
        System.out.println("Полученный водитель: " + newUserDto);
        driverService.create(
                DriverCreateDto.builder()
                        .firstName(newUserDto.firstName())
                        .lastName(newUserDto.lastName())
                        .build()
        );
    }
}