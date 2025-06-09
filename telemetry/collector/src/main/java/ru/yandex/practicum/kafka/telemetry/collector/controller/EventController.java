package ru.yandex.practicum.kafka.telemetry.collector.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.hub.HubEventType;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEvent;
import ru.yandex.practicum.kafka.telemetry.collector.model.sensor.SensorEventType;
import ru.yandex.practicum.kafka.telemetry.collector.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.collector.service.handler.sensor.SensorEventHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/events")
public class EventController {
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;
    private final Map<SensorEventType, SensorEventHandler> sensorEventHandlers;

    public EventController(List<HubEventHandler> hubEventHandlers, List<SensorEventHandler> sensorEventHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
    }

    @PostMapping("/sensors")
    public void sensorEvent(@Valid @RequestBody SensorEvent event) {
        log.warn("SensorEvent: {}", event);
        SensorEventHandler sensorEventHandler = sensorEventHandlers.get(event.getType());
        if (sensorEventHandler == null) {
            throw new IllegalArgumentException("Не найден обработчик для события: " + event.getType());
        }
        sensorEventHandler.handle(event);
    }

    @PostMapping("/hubs")
    public void hubEvent(@Valid @RequestBody HubEvent event) {
        log.warn("HubEvent: {}", event);
        HubEventHandler hubEventHandler = hubEventHandlers.get(event.getType());
        if (hubEventHandler == null) {
            throw new IllegalArgumentException("Не найден обработчик для события: " + event.getType());
        }
        hubEventHandler.handle(event);
    }
}
