package ru.yandex.practicum.kafka.telemetry.collector.controller;

import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.collector.service.handler.hub.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.collector.service.handler.sensor.SensorEventHandler;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@GrpcService
public class EventController extends CollectorControllerGrpc.CollectorControllerImplBase {
    private final Map<HubEventProto.PayloadCase, HubEventHandler> hubEventHandlers;
    private final Map<SensorEventProto.PayloadCase, SensorEventHandler> sensorEventHandlers;

    public EventController(Set<HubEventHandler> hubEventHandlers, Set<SensorEventHandler> sensorEventHandlers) {
        this.hubEventHandlers = hubEventHandlers.stream()
                .collect(Collectors.toMap(HubEventHandler::getMessageType, Function.identity()));
        this.sensorEventHandlers = sensorEventHandlers.stream()
                .collect(Collectors.toMap(SensorEventHandler::getMessageType, Function.identity()));
    }

    @Override
    public void collectSensorEvent(SensorEventProto event, StreamObserver<Empty> responseObserver) {
        try {
            log.warn("SensorEvent: {}", event);
            SensorEventHandler sensorEventHandler = sensorEventHandlers.get(event.getPayloadCase());
            if (sensorEventHandler == null) {
                throw new IllegalArgumentException("Не найден обработчик для события: " + event.getPayloadCase());
            }
            sensorEventHandler.handle(event);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }

    @Override
    public void collectHubEvent(HubEventProto event, StreamObserver<Empty> responseObserver) {
        try {
            log.warn("SensorEvent: {}", event);
            HubEventHandler hubEventHandler = hubEventHandlers.get(event.getPayloadCase());
            if (hubEventHandler == null) {
                throw new IllegalArgumentException("Не найден обработчик для события: " + event.getPayloadCase());
            }
            hubEventHandler.handle(event);
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new StatusRuntimeException(Status.fromThrowable(e)));
        }
    }
}
