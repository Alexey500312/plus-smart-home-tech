package ru.yandex.practicum.kafka.telemetry.collector.model.hub;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ScenarioCondition {
    private String sensorId;
    private ScenarioConditionType type;
    private OperationType operation;
    private int value;
}
