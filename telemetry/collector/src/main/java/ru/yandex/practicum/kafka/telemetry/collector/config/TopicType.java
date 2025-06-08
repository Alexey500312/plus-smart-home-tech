package ru.yandex.practicum.kafka.telemetry.collector.config;

public enum TopicType {
    SENSORS_EVENTS,
    HUBS_EVENTS;

    public static TopicType toTopicsType(String type) {
        for (TopicType value : values()) {
            if (value.name().equalsIgnoreCase(type.replace("-", "_"))) {
                return value;
            }
        }
        return null;
    }
}
