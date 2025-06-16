package ru.yandex.practicum.kafka.telemetry.analyzer.service.client;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import ru.yandex.practicum.kafka.telemetry.analyzer.config.TopicType;

import java.util.EnumMap;

public interface Client {
    Consumer<String, SpecificRecordBase> getConsumer();

    EnumMap<TopicType, String> getTopics();

    void stop();
}
