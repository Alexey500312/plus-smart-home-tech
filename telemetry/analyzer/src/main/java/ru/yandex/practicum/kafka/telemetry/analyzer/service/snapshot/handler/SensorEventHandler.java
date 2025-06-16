package ru.yandex.practicum.kafka.telemetry.analyzer.service.snapshot.handler;

import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.analyzer.db.condition.Condition;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

import java.util.Collection;

public interface SensorEventHandler {
    SensorEventType getMessageType();

    boolean check(SensorStateAvro sensorState, Collection<Condition> conditions);

    Collection<DeviceActionRequest> getDeviceActionRequest(SensorEventHandlerParams params);
}
