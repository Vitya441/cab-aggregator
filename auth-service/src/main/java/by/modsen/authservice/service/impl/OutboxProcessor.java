package by.modsen.authservice.service.impl;

import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.entity.OutboxMessage;
import by.modsen.authservice.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, NewUserDto> kafkaTemplate;

    @Scheduled(cron = "${app.scheduler.cron-every-5-seconds}")
    @Transactional
    public void processOutboxMessages() {
        List<OutboxMessage> messages = outboxRepository.findBySentFalse();

        for (OutboxMessage message : messages) {
            try {
                NewUserDto newUserDto = new ObjectMapper().readValue(message.getPayload(), NewUserDto.class);
                kafkaTemplate.send(message.getTopic(), newUserDto).get();
                message.setSent(true);
                outboxRepository.save(message);
            } catch (Exception e) {
                log.error("Failed to send outbox message: {}", message, e);
            }
        }
    }
}