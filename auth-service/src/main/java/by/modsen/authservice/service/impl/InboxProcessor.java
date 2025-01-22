package by.modsen.authservice.service.impl;

import by.modsen.authservice.dto.NewUserDto;
import by.modsen.authservice.dto.PayloadWrapper;
import by.modsen.authservice.dto.UserRole;
import by.modsen.authservice.entity.InboxMessage;
import by.modsen.authservice.entity.InboxStatus;
import by.modsen.authservice.entity.OutboxMessage;
import by.modsen.authservice.kafka.KafkaTopicsProperties;
import by.modsen.authservice.repository.InboxRepository;
import by.modsen.authservice.repository.OutboxRepository;
import by.modsen.authservice.service.RoleService;
import by.modsen.authservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InboxProcessor {

    private final InboxRepository inboxRepository;
    private final OutboxRepository outboxRepository;
    private final UserService userService;
    private final RoleService roleService;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    @Scheduled(cron = "${app.scheduler.cron-every-5-seconds}")
    @Transactional
    public void processInboxMessages() {
        List<InboxMessage> messages = inboxRepository.findByStatusNot(InboxStatus.PROCESSED);

        for (InboxMessage inbox : messages) {
            try {
                PayloadWrapper payloadWrapper = new ObjectMapper().readValue(inbox.getPayload(), PayloadWrapper.class);
                NewUserDto newUserDto = payloadWrapper.user();
                UserRole userRole = payloadWrapper.role();

                if (inbox.getUserId() == null) {
                    if (userService.isUserDataTaken(newUserDto.email(), newUserDto.username())) {
                        inbox.setStatus(InboxStatus.PROCESSED);
                        inboxRepository.save(inbox);
                        continue;
                    }
                    String userId = createUserInKeycloak(newUserDto, userRole);
                    inbox.setUserId(userId);
                }

                OutboxMessage outboxMessage = new OutboxMessage();
                outboxMessage.setTopic(getTopic(userRole));
                outboxMessage.setPayload(new ObjectMapper().writeValueAsString(newUserDto));
                outboxRepository.save(outboxMessage);

                inbox.setStatus(InboxStatus.PROCESSED);
                inboxRepository.save(inbox);
            } catch (Exception e) {
                log.error("Failed to process inbox message: {}", inbox, e);
                inbox.setStatus(InboxStatus.FAILED);
                inboxRepository.save(inbox);
            }
        }
    }

    private String createUserInKeycloak(NewUserDto newUserDto, UserRole userRole) {
        String userId = userService.createUser(newUserDto);
        roleService.assignRole(userId, userRole.name());

        return userId;
    }

    private String getTopic(UserRole userRole) {
        if (userRole == UserRole.PASSENGER) {
            return kafkaTopicsProperties.getPassenger().getName();
        } else if (userRole == UserRole.DRIVER) {
            return kafkaTopicsProperties.getDriver().getName();
        }
        throw new IllegalArgumentException("Unknown user role: " + userRole);
    }
}