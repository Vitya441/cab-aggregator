package by.modsen.driverservice.config;

import by.modsen.commonmodule.dto.DriverStatusEvent;
import by.modsen.commonmodule.dto.RequestedRideEvent;
import by.modsen.driverservice.kafka.NewUserDto;
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

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${app.kafka.consumer.group-id}")
    private String driverGroupId;

    @Value("${app.kafka.consumer-2.group-id}")
    private String ridesGroupId;

    @Value("${app.kafka.consumer.type-mappings}")
    private String typeMappings;

    @Bean
    public ConsumerFactory<String, NewUserDto> driverConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, driverGroupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.TYPE_MAPPINGS, typeMappings);

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(NewUserDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NewUserDto>
    driverKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NewUserDto> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(driverConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, RequestedRideEvent> ridesConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ridesGroupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(RequestedRideEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RequestedRideEvent>
    ridesKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RequestedRideEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ridesConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DriverStatusEvent> driverStatusConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ridesGroupId);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(DriverStatusEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DriverStatusEvent>
    driverStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DriverStatusEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(driverStatusConsumerFactory());
        return factory;
    }
}