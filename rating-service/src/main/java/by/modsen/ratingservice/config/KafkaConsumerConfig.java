package by.modsen.ratingservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${app.kafka.passenger-rating-consumer.groupId}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, Long> passengerRatingConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new LongDeserializer()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Long>
    passengerRatingKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Long> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(passengerRatingConsumerFactory());

        return factory;
    }
}
