package ru.yandex.practicum.kafka.telemetry.analyzer.service.client;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.analyzer.config.KafkaConfig;
import ru.yandex.practicum.kafka.telemetry.analyzer.config.TopicType;

import java.util.EnumMap;

@Component("snapshotClient")
@RequiredArgsConstructor
public class SnapshotClient implements Client {
    private final KafkaConfig config;
    private Consumer<String, SpecificRecordBase> consumer;

    @Override
    public Consumer<String, SpecificRecordBase> getConsumer() {
        if (consumer == null) {
            init();
        }
        return consumer;
    }

    @Override
    public EnumMap<TopicType, String> getTopics() {
        return config.getSnapshotTopics();
    }

    @Override
    public void stop() {
        if (consumer != null) {
            consumer.close();
        }
    }

    private void init() {
        consumer = new KafkaConsumer<>(config.getSnapshotProperties());
    }
}
