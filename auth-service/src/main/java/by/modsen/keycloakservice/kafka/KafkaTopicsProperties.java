package by.modsen.keycloakservice.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics")
public class KafkaTopicsProperties {

    private final TopicConfig passenger = new TopicConfig();
    private final TopicConfig driver = new TopicConfig();

    public TopicConfig getPassenger() {
        return passenger;
    }

    public TopicConfig getDriver() {
        return driver;
    }

    public static class TopicConfig {
        private String name;
        private int partitions;
        private short replicas;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPartitions() {
            return partitions;
        }

        public void setPartitions(int partitions) {
            this.partitions = partitions;
        }

        public short getReplicas() {
            return replicas;
        }

        public void setReplicas(short replicas) {
            this.replicas = replicas;
        }
    }
}
