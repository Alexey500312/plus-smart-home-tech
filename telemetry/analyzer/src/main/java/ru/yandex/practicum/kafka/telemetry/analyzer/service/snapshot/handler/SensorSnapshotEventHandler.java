package ru.yandex.practicum.kafka.telemetry.analyzer.service.snapshot.handler;

import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public interface SensorSnapshotEventHandler {
    void handle(SensorsSnapshotAvro snapshot);
}
