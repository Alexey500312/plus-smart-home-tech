package ru.yandex.practicum.kafka.telemetry.aggregator.service.client;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;
import ru.yandex.practicum.kafka.telemetry.aggregator.config.TopicType;

import java.util.EnumMap;

public interface Client {
    Producer<String, SpecificRecordBase> getProducer();

    EnumMap<TopicType, String> getProducerTopics();

    Consumer<String, SpecificRecordBase> getConsumer();

    EnumMap<TopicType, String> getConsumerTopics();

    void stop();
}
