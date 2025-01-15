package by.modsen.ridesservice.config;

import by.modsen.commonmodule.dto.DriverNotificationEvent;
import by.modsen.commonmodule.dto.DriverStatusEvent;
import by.modsen.commonmodule.dto.RequestedRideEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, RequestedRideEvent> ridesProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, RequestedRideEvent> ridesKafkaTemplate() {
        return new KafkaTemplate<>(ridesProducerFactory());
    }

    @Bean
    public ProducerFactory<String, DriverStatusEvent> driverStatusProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, DriverStatusEvent> driverStatusKafkaTemplate() {
        return new KafkaTemplate<>(driverStatusProducerFactory());
    }

    @Bean
    public ProducerFactory<String, DriverNotificationEvent> driverNotificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, DriverNotificationEvent> driverNotificationKafkaTemplate() {
        return new KafkaTemplate<>(driverNotificationProducerFactory());
    }
}