package ru.yandex.practicum.kafka.telemetry.aggregator.config;

public enum TopicType {
    SENSORS_EVENTS,
    SENSORS_SNAPSHOTS;

    public static TopicType toTopicsType(String type) {
        for (TopicType value : values()) {
            if (value.name().equalsIgnoreCase(type.replace("-", "_"))) {
                return value;
            }
        }
        return null;
    }
}