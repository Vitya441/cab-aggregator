package by.modsen.keycloakservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KafkaTopicsProperties.class)
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    @Bean
    public NewTopic passengerTopic() {
        KafkaTopicsProperties.TopicConfig passenger = kafkaTopicsProperties.getPassenger();
        return new NewTopic(passenger.getName(), passenger.getPartitions(), passenger.getReplicas());
    }

    @Bean
    public NewTopic driverTopic() {
        KafkaTopicsProperties.TopicConfig driver = kafkaTopicsProperties.getDriver();
        return new NewTopic(driver.getName(), driver.getPartitions(), driver.getReplicas());
    }
}