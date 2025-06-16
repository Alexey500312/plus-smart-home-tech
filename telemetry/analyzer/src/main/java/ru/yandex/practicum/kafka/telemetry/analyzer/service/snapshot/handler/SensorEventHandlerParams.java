package ru.yandex.practicum.kafka.telemetry.analyzer.service.snapshot.handler;

import lombok.Builder;
import lombok.Getter;
import ru.yandex.practicum.kafka.telemetry.analyzer.db.scenario.Scenario;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;

import java.util.Collection;

@Getter
@Builder(toBuilder = true)
public class SensorEventHandlerParams {
    private String hubId;
    private String sensorId;
    private SensorStateAvro sensorState;
    private Collection<Scenario> scenarios;
}
