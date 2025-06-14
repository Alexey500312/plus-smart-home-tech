package ru.yandex.practicum.kafka.telemetry.aggregator.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;

public interface Client {
    Producer<String, SpecificRecordBase> getProducer();

    Consumer<String, SpecificRecordBase> getConsumer();

    void stop();
}
