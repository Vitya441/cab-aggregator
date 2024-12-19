package by.modsen.driverservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${app.kafka.producer.topic}")
    private String availableDriversTopic;

    @Bean
    public NewTopic availableDriversTopic() {
        return TopicBuilder.name(availableDriversTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}