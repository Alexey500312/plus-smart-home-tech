package ru.yandex.practicum.kafka.telemetry.analyzer.config;

import java.util.EnumMap;
import java.util.Properties;

public interface HubEventConsumer {
    Properties getHubProperties();

    EnumMap<TopicType, String> getHubTopics();
}
