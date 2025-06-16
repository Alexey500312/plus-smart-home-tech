package ru.yandex.practicum.kafka.telemetry.analyzer.config;

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
@ConfigurationProperties("analyzer.kafka")
public class KafkaConfig implements HubEventConsumer, SnapshotsConsumer {
    private HubConfig hubConsumer;
    private SnapshotConfig snapshotConsumer;

    @Override
    public Properties getHubProperties() {
        return hubConsumer.getProperties();
    }

    @Override
    public EnumMap<TopicType, String> getHubTopics() {
        return hubConsumer.getTopics();
    }

    @Override
    public Properties getSnapshotProperties() {
        return snapshotConsumer.getProperties();
    }

    @Override
    public EnumMap<TopicType, String> getSnapshotTopics() {
        return snapshotConsumer.getTopics();
    }

    @Getter
    public static class HubConfig {
        private final Properties properties;
        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);

        public HubConfig(Properties properties, Map<String, String> topics) {
            this.properties = properties;
            for (Map.Entry<String, String> entry : topics.entrySet()) {
                this.topics.put(TopicType.toTopicsType(entry.getKey()), entry.getValue());
            }
        }
    }

    @Getter
    public static class SnapshotConfig {
        private final Properties properties;
        private final EnumMap<TopicType, String> topics = new EnumMap<>(TopicType.class);

        public SnapshotConfig(Properties properties, Map<String, String> topics) {
            this.properties = properties;
            for (Map.Entry<String, String> entry : topics.entrySet()) {
                this.topics.put(TopicType.toTopicsType(entry.getKey()), entry.getValue());
            }
        }
    }
}
