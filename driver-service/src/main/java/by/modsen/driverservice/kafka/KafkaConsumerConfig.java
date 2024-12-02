package by.modsen.driverservice.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
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

    private final String bootstrapServers;

    private final String groupId;

    private final String typeMappings;

    public KafkaConsumerConfig(
            @Value("${app.kafka.consumer.bootstrap-servers}")
            String bootstrapServers,
            @Value("${app.kafka.consumer.group-id}")
            String groupId,
            @Value("${app.kafka.consumer.type-mappings}")
            String typeMappings) {
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
        this.typeMappings = typeMappings;
    }

    @Bean
    ConsumerFactory<String, NewUserDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, typeMappings);

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