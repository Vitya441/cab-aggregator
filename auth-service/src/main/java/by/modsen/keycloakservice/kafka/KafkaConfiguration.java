package by.modsen.keycloakservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic passengerTopic() {
        return new NewTopic("passenger-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic driverTopic() {
        return new NewTopic("driver-topic", 1, (short) 1);
    }
}