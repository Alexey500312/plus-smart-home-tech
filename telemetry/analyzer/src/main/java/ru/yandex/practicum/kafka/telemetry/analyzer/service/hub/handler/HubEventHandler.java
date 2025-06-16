package ru.yandex.practicum.kafka.telemetry.analyzer.service.hub.handler;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {
    HubEventType getMessageType();

    void handle(HubEventAvro event);
}
