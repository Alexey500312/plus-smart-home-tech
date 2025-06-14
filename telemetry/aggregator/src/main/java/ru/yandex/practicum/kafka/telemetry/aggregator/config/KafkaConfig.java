package ru.yandex.practicum.kafka.telemetry.aggregator.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

@Getter
@Setter
@ToString
@ConfigurationProperties("aggregator.kafka")
public class KafkaConfig implements KafkaProducer, KafkaConsumer {
    private ProducerConfig producer;
    private ConsumerConfig consumer;

    @Override
    public Properties getProducerProperties() {
        return producer.getProperties();
    }

    @Override
    public EnumMap<TopicType, String> getProducerTopics() {
        return producer.getTopics();
    }

    @Override
    public Properties getConsumerProperties() {
        return consumer.getProperties();
    }

    @Override
    public EnumMap<TopicType, String> getConsumerTopics() {
        return consumer.getTopics();
    }

    @Getter
    public static class ProducerConfig {
        private final Properties properties;
        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);

        public ProducerConfig(Properties properties, Map<String, String> topics) {
            this.properties = properties;
            for (Map.Entry<String, String> entry : topics.entrySet()) {
                this.topics.put(TopicType.toTopicsType(entry.getKey()), entry.getValue());
            }
        }
    }

    @Getter
    public static class ConsumerConfig {
        private final Properties properties;
        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);

        public ConsumerConfig(Properties properties, Map<String, String> topics) {
            this.properties = properties;
            for (Map.Entry<String, String> entry : topics.entrySet()) {
                this.topics.put(TopicType.toTopicsType(entry.getKey()), entry.getValue());
            }
        }
    }
}
