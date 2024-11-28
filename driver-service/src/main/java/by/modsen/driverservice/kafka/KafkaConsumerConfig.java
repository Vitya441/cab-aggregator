package by.modsen.driverservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    private final String bootstrapServers = "localhost:9092";
    private final String groupId = "driver-group";

    @Bean
    ConsumerFactory<String, NewUserDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId
        );
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );
        props.put(
                JsonDeserializer.TYPE_MAPPINGS,
                "by.modsen.keycloakservice.dto.NewUserDto:by.modsen.driverservice.kafka.NewUserDto"
        );

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(NewUserDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NewUserDto>
    kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NewUserDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}