package ru.yandex.practicum.kafka.telemetry.aggregator.config;

import java.util.EnumMap;
import java.util.Properties;

public interface KafkaConsumer {
    Properties getConsumerProperties();

    EnumMap<TopicType, String> getConsumerTopics();
}
