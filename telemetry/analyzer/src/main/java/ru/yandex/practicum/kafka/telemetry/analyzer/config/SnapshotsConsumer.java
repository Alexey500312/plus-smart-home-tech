package ru.yandex.practicum.kafka.telemetry.analyzer.config;

import java.util.EnumMap;
import java.util.Properties;

public interface SnapshotsConsumer {
    Properties getSnapshotProperties();

    EnumMap<TopicType, String> getSnapshotTopics();
}
