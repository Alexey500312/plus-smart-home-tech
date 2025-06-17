package ru.yandex.practicum.kafka.telemetry.aggregator.service.client;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.aggregator.config.KafkaConfig;
import ru.yandex.practicum.kafka.telemetry.aggregator.config.TopicType;

import java.util.EnumMap;

@Component
@RequiredArgsConstructor
public class AggregatorClient implements Client {
    private final KafkaConfig config;
    private Producer<String, SpecificRecordBase> producer;
    private Consumer<String, SpecificRecordBase> consumer;

    @Override
    public Producer<String, SpecificRecordBase> getProducer() {
        if (producer == null) {
            initProducer();
        }
        return producer;
    }

    @Override
    public EnumMap<TopicType, String> getProducerTopics() {
        return config.getProducerTopics();
    }

    @Override
    public Consumer<String, SpecificRecordBase> getConsumer() {
        if (consumer == null) {
            initConsumer();
        }
        return consumer;
    }

    @Override
    public EnumMap<TopicType, String> getConsumerTopics() {
        return config.getConsumerTopics();
    }

    @Override
    public void stop() {
        if (producer != null) {
            producer.close();
        }
        if (consumer != null) {
            consumer.close();
        }
    }

    private void initProducer() {
        producer = new KafkaProducer<>(config.getProducerProperties());
    }

    private void initConsumer() {
        consumer = new KafkaConsumer<>(config.getConsumerProperties());
    }
}
