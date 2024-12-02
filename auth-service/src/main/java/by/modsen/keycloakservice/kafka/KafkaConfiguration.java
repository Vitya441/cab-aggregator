package by.modsen.keycloakservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

    private final String passengerTopicName;

    private final int passengerTopicPartitions;

    private final short passengerTopicReplicas;

    private final String driverTopicName;

    private final int driverTopicPartitions;

    private final short driverTopicReplicas;

    public KafkaConfiguration(
            @Value("${app.kafka.topics.passenger.name}")
            String passengerTopicName,
            @Value("${app.kafka.topics.passenger.partitions}")
            int passengerTopicPartitions,
            @Value("${app.kafka.topics.passenger.replicas}")
            short passengerTopicReplicas,
            @Value("${app.kafka.topics.driver.name}")
            String driverTopicName,
            @Value("${app.kafka.topics.driver.partitions}")
            int driverTopicPartitions,
            @Value("${app.kafka.topics.driver.replicas}")
            short driverTopicReplicas) {
        this.passengerTopicName = passengerTopicName;
        this.passengerTopicPartitions = passengerTopicPartitions;
        this.passengerTopicReplicas = passengerTopicReplicas;
        this.driverTopicName = driverTopicName;
        this.driverTopicPartitions = driverTopicPartitions;
        this.driverTopicReplicas = driverTopicReplicas;
    }

    @Bean
    public NewTopic passengerTopic() {
        return new NewTopic(passengerTopicName, passengerTopicPartitions, passengerTopicReplicas);
    }

    @Bean
    public NewTopic driverTopic() {
        return new NewTopic(driverTopicName, driverTopicPartitions, driverTopicReplicas);
    }
}